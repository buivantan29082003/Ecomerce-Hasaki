package com.CloneShopee.controllers.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.CloneShopee.Bean.ShopBean;
import com.CloneShopee.DTO.User.OrderInsertDTO;
import com.CloneShopee.DTO.User.OrderItemDTO;
import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.ResponeEntity.PageList;
import com.CloneShopee.config.Payment;
import com.CloneShopee.models.Address;
import com.CloneShopee.models.Order;
import com.CloneShopee.models.OrderItem;
import com.CloneShopee.models.PromotionItem;
import com.CloneShopee.models.Status;
import com.CloneShopee.models.Transaction;
import com.CloneShopee.services.Common.TransactionService;
import com.CloneShopee.services.User.AddressService;
import com.CloneShopee.services.User.CartItemService;
import com.CloneShopee.services.User.ProductServiceUser;
import com.CloneShopee.services.User.UserOrderService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
public class UserOrderController {

    @Autowired
    UserOrderService orderService;
    @Autowired
    ShopBean shopBean;

    @Autowired
    AddressService addressService;

    @Autowired
    TransactionService tranService;

    @Autowired
    ProductServiceUser productServiceUser;

    @Autowired
    CartItemService cartItemService;

    private final com.CloneShopee.models.Payment paymentOnline = new com.CloneShopee.models.Payment(3);
    private final com.CloneShopee.models.Payment cod = new com.CloneShopee.models.Payment(4);
    private Map statusNotCancel = Map.of(13, 3, 14, 2, 15, 2, 16, 1);

    @Transactional
    @PostMapping("/user/order/cancel")
    public ResponseEntity<Object> cancelOrder(
            @RequestParam(value = "orderId", required = true, defaultValue = "-1") Integer orderId,
            @RequestParam(value = "reasonCancel", defaultValue = "Khác") String reasonCancel) {
        Order order = orderService.getOrderByOrderIdAndAccountId(orderId);
        if (order != null) {
            if (statusNotCancel.get(order.getStatus().getId()) != null) {
                return new ResponseEntity<>(
                        new BaseRespone(null, "  Trang thái đơn hàng hiện tại không được phép hủy."),
                        HttpStatus.BAD_REQUEST);
            }
            Transaction tran = tranService.getTransactionByOrderId(orderId);
            if (tran != null && tran.isWithin15Minutes()) {
                return new ResponseEntity<>(new BaseRespone(null,
                        "Giao dịch thanh toán đang diễn ra (15p) vui lòng đợi sau khoảng thời gian này để hủy đơn."),
                        HttpStatus.BAD_REQUEST);

            }
            orderService.cancelOrder(orderId, reasonCancel);
            if (tran != null) {
                if (tran.getTransactionStatus() == 1) {
                    if (!orderService.refundOrder(order, tran)) {
                        return new ResponseEntity<>(
                                new BaseRespone(null, "Giao dịch hoàn tiền thất bại vui lòng thử lại sau"),
                                HttpStatus.OK);
                    }
                }
            }
            return new ResponseEntity<>(new BaseRespone(null, "Yêu cầu hủy đơn thành công"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseRespone(null, "Không tìm thấy đơn hàng của bạn."), HttpStatus.BAD_REQUEST);
    }

    @Transactional
    @GetMapping("/callback/vnpay")
    public ResponseEntity<Object> callBackPayment(@RequestParam Map<String, String> allParams) {
        // String vnp_TmnCode = allParams.get("vnp_TmnCode");
        // String vnp_Amount = allParams.get("vnp_Amount");
        // String vnp_BankCode = allParams.get("vnp_BankCode");
        // String vnp_BankTranNo = allParams.get("vnp_BankTranNo");
        // String vnp_CardType = allParams.get("vnp_CardType");
        System.out.println("============== CALL BACK ================");
        String vnp_OrderInfo = allParams.get("vnp_OrderInfo");
        String vnp_PayDate = allParams.get("vnp_PayDate");
        System.out.println(vnp_PayDate);
        String vnp_ResponseCode = allParams.get("vnp_ResponseCode");
        String vnp_TransactionNo = allParams.get("vnp_TransactionNo");
        String vnp_TransactionStatus = allParams.get("vnp_TransactionStatus");
        // String vnp_TxnRef = allParams.get("vnp_TxnRef");
        // String vnp_SecureHash = allParams.get("vnp_SecureHash");

        // Kiểm tra kết quả thanh toán
        if ("00".equals(vnp_ResponseCode) && "00".equals(vnp_TransactionStatus)) {
            Order order = orderService.checkOrderIsValidToGenerateUrlPayment(Integer.parseInt(vnp_OrderInfo));
            orderService.updateStatusOrder(order.getId(), 10);
            Transaction p = tranService.getTransactionByOrderId(Integer.parseInt(vnp_OrderInfo));
            p.setTotalAmmount(order.getTotalAmount());
            p.setTransactionId(Integer.parseInt(vnp_TransactionNo));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            LocalDateTime dateTime = LocalDateTime.parse(vnp_PayDate, formatter);
            p.setCreatedDate(dateTime);
            p.setTransactionStatus(1);
            p.setMessage("Thanh toán cho đơn hàng: " + order.getId());
            tranService.saveTransaction(p);
            return ResponseEntity.ok(Map.of("RspCode", "00", "Message", "Confirm Success"));
        } else {
            System.out.println(">>> Thanh toán thất bại hoặc bị hủy!");
            return ResponseEntity.ok(Map.of("RspCode", "01", "Message", "Transaction Failed"));
        }
    }

    @Transactional
    @GetMapping("/user/order/geturlpayment")
    public ResponseEntity<Object> getUrlPayment(@RequestParam(value = "orderId", defaultValue = "-1") Integer orderId,
            @RequestParam(value = "methodCode", defaultValue = "VNPAY") String methodCode) {
        Order order = orderService.checkOrderIsValidToGenerateUrlPayment(orderId);
        if (!order.isOrderCreatedWithin10Minutes()) {
            return new ResponseEntity<>(new BaseRespone(null, "Đơn hàng đã quá hạn yêu cầu thanh toán !!!"),
                    HttpStatus.BAD_REQUEST);
        }
        if (order != null) {
            // check có link chưa
            Transaction tran = tranService.getTransactionByOrderId(orderId);
            if (tran == null) {
                tran = new Transaction();
                tran.setOrder(order);
                tran.setPayOrRefund(1);
                tran.setCreatedTransaction(LocalDateTime.now());
                Boolean a = tranService.createTransaction(tran, methodCode);
                if (a) {
                    return new ResponseEntity<>(new BaseRespone(tran.getTransactionlink(), "success"), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(
                            new BaseRespone(null, "Xử lý yêu cầu không thành công vui lòng thử lại"),
                            HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<>(new BaseRespone(tran.getTransactionlink(), "success"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseRespone(null, "Yêu cầu thanh toán đơn hàng không hợp lệ"),
                HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/user/order/search")
    public ResponseEntity<Object> getOrder(@RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "keyType", required = false) String typeKey,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "status", defaultValue = "-1", required = false) Integer statusId) {
        Sort sort = Sort.by("id").descending();
        if (sortBy != null && sortBy.equals("total")) {
            sort = Sort.by("totalAmmount").descending();
        }
        Pageable pageable = PageRequest.of(page != null && page > -1 ? page : 0, 5, sort);

        PageList p = orderService.searchOrder(key, typeKey, pageable, statusId);
        return new ResponseEntity<>(new BaseRespone(p, "success"), HttpStatus.OK);
    }

    @Transactional
    @PostMapping("user/order/add")
    public ResponseEntity<Object> addOrder(@RequestBody @Valid OrderInsertDTO order) {
        Address address = addressService.getAddressByAccountIdAndAddressId(shopBean.getAccount().getId(),
                order.getAddressId());
        if (address == null) {
            return new ResponseEntity<>(new BaseRespone(null, "Địa chỉ không hợp lệ"), HttpStatus.BAD_REQUEST);
        }
        List<OrderItemDTO> orderItems = orderService.generationOrderInListIds(order.getCartItems(),
                shopBean.getAccountId());
        // đúng là cart của user
        if (orderItems.size() == order.getCartItems().size()) {
            // group sản phẩm theo shop
            Map<Integer, List<OrderItemDTO>> mapShop = orderItems.stream().distinct()
                    .collect(Collectors.groupingBy(OrderItemDTO::getShopId));
            // Lấy danh sách id của các sản phẩm để lấy các khuyến mãi
            List<Integer> productIds = orderItems.stream().map(v -> v.getProductVariantId()).toList();
            // lấy danh sách các khuyến mãi và group theo sản phẩm

            List<PromotionItem> promotions = productServiceUser.getAllPromotionByProductVariantIds(productIds);

            Map<Integer, PromotionItem> groupedByProduct = new HashMap<>();
            promotions.forEach(v -> {
                groupedByProduct.put(v.getProduct().getId(), v);
            });
            List<Order> orders = new ArrayList<>();
            Status status;
            if (order.getPaymentType().equals("ONLINE")) {
                status = new Status(9);
            } else {
                status = new Status(10);
            }
            List<OrderItem> orderItemss = new ArrayList<>();
            com.CloneShopee.models.Payment p = order.getPaymentType().equals("ONLINE") ? paymentOnline : cod;
            order.getShops().forEach(v -> {
                orderService.proccessOrder(v, mapShop.get(v.getShopId()), groupedByProduct, orders, status,
                        orderItemss, p, address.getFullAddress());
            });
            try {
                orderService.saveOrder(orders, orderItemss);
                cartItemService.deleteCartInList(order.getCartItems());
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(
                        new BaseRespone(null, "Có lỗi xảy ra trong quá trình tạo đơn hàng, vui lòng kiểm tra lại"),
                        HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(new BaseRespone(null, "success"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseRespone(null, "Danh sách sản phẩm yêu cầu không hợp lệ"),
                HttpStatus.BAD_REQUEST);
    }

}