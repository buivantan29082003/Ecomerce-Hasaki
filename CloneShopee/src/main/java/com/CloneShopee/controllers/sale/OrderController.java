package com.CloneShopee.controllers.sale;

import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CloneShopee.Bean.ShopBean;
import com.CloneShopee.ExceptionGlobal.ConstraintException;
import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.ResponeEntity.PageList;
import com.CloneShopee.models.Order;
import com.CloneShopee.models.Transaction;
import com.CloneShopee.services.Common.PaymentVNPayService;
import com.CloneShopee.services.Common.TransactionService;
import com.CloneShopee.services.sale.OrderService;
import com.CloneShopee.services.sale.StatusCustome;

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin("*")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    ShopBean shopBean;

    @Autowired
    TransactionService tranService;

    private Map statusNotCancel = Map.of(13, 3, 14, 2, 15, 2, 16, 1);

    @Transactional
    @PostMapping("/sale/order/cancel")
    public ResponseEntity<Object> cancelOrder(
            @RequestParam(value = "orderId", required = true, defaultValue = "-1") Integer orderId,
            @RequestParam(value = "reasonCancel", defaultValue = "Khác") String reasonCancel) {
        Order order = orderService.getOrderByShopId(orderId);
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

    @GetMapping("sale/order/findall")
    public ResponseEntity<Object> getAllOrder() {
        return new ResponseEntity<>(orderService.getAllOrder(), HttpStatus.OK);
    }

    @GetMapping("sale/order/finditems")
    public ResponseEntity<Object> getAllOrder(@RequestParam(name = "orderId", defaultValue = "-1") Integer orderId) {
        return new ResponseEntity<>(orderService.getOrderItemByOrderId(orderId, shopBean.getShop().getId()),
                HttpStatus.OK);
    }

    @GetMapping("/sale/order/search")
    public ResponseEntity<Object> getOrder(@RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "keyType", required = false) String typeKey,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "status", defaultValue = "-1", required = false) Integer statusId,
            @RequestParam(value = "methodPayment", defaultValue = "-1", required = false) Integer paymentMethod) {
        Sort sort = Sort.by("id").descending();
        if (sortBy != null && sortBy.equals("total")) {
            sort = Sort.by("totalAmmount").descending();
        }
        Pageable pageable = PageRequest.of(page != null && page > -1 ? page : 0, 5, sort);

        PageList p = orderService.searchOrder(key, typeKey, pageable, statusId, paymentMethod);
        return new ResponseEntity<>(new BaseRespone(p, "success"), HttpStatus.OK);
    }

    @Transactional
    @PutMapping("sale/order/update/status")
    public ResponseEntity<Object> updateStatusOrders(
            @RequestParam(name = "status", defaultValue = "") String statusName,
            @RequestBody Set<Integer> orderIds) {
        StatusCustome status = OrderService.statusCode.get(statusName);
        if (status != null && status.getStatusId() > 1 && status.getStatusId() < 4) {
            orderService.checkStatusOrdersAndOfShop(orderIds, shopBean.getShop().getId(), status.getStatusId() - 1);
            orderService.updateStatusAndTagOrder(orderIds, status.getStatusId(), "");
            return new ResponseEntity<>("Update status order successfully", HttpStatus.OK);
        }
        throw new ConstraintException("status", "Status code is not valid");
    }

    @Transactional
    @PostMapping("sale/order/continue")
    public ResponseEntity<Object> continueProcess(
            @RequestParam(value = "orderId", defaultValue = "-1", required = true) Integer orderId) {
        Order order = orderService.getOrderByShopId(orderId);
        if (order != null) {
            if (order.getStatus().getId() == 10 || order.getStatus().getId() == 11 || order.getStatus().getId() == 12) {
                orderService.updateStatusOrder(orderId, order.getStatus().getId() + 1);
                return new ResponseEntity<>(new BaseRespone(null, "Cập nhật đơn hàng thành công"), HttpStatus.OK);
            }
            return new ResponseEntity<>(new BaseRespone(null, "Trạng thái đơn hàng không thể chuyển tiếp"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new BaseRespone(null, "Không tìm thấy đơn hàng của bạn"), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("sale/order/update/status/single")
    @Transactional
    public ResponseEntity<Object> updateStatusOrder(@RequestParam(name = "status", defaultValue = "") String statusName,
            @RequestBody Integer orderId) {
        StatusCustome status = OrderService.statusCode.get(statusName);
        if (status != null && status.getStatusId() > 1 && status.getStatusId() < 4) {
            if (orderService.checkOrderOfShop(orderId, shopBean.getShop().getId(), status.getStatusId())) {
                orderService.updateStatusAndTagOrder(orderId, status.getStatusId(), "");
                return new ResponseEntity<>("Update status order successfully", HttpStatus.OK);
            }
            return new ResponseEntity<>("Order with status is not valid", HttpStatus.OK);
        }
        return new ResponseEntity<>("Status code is not valid", HttpStatus.OK);
    }

    @GetMapping("getProduct")
    public ResponseEntity<Object> addProductForLive() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
