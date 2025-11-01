package com.CloneShopee.services.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.CloneShopee.Bean.ShopBean;
import com.CloneShopee.DTO.User.OrderInfo;
import com.CloneShopee.DTO.User.OrderItemDTO;
import com.CloneShopee.DTO.User.ShopItemDTO;
import com.CloneShopee.DTO.User.OrderResponse.OrderDTO;
import com.CloneShopee.ResponeEntity.PageList;
import com.CloneShopee.models.Order;
import com.CloneShopee.models.OrderItem;
import com.CloneShopee.models.PromotionItem;
import com.CloneShopee.models.Shop;
import com.CloneShopee.models.Status;
import com.CloneShopee.models.VoucherLive;
import com.CloneShopee.models.VoucherShop;
import com.CloneShopee.models.VoucherShopAccount;
import com.CloneShopee.repository.CartItemRepository;
import com.CloneShopee.repository.OrderItemRepository;
import com.CloneShopee.repository.OrderRepository;
import com.CloneShopee.repository.ProductRepository;
import com.CloneShopee.repository.PromotionItemRepository;
import com.CloneShopee.repository.PromotionRepository;
import com.CloneShopee.repository.VoucherRepository;

@Service
public class UserOrderService {
    @Autowired
    CartItemRepository cartItemRepo;

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    ShopBean shopBean;

    @Autowired
    PromotionItemRepository pro;

    @Autowired
    OrderRepository orderRepo;

    @Autowired
    OrderItemRepository orderItemRepo;

    @Autowired
    PromotionRepository promotionRepo;

    @Autowired
    VoucherRepository voucherRepo;

    public PageList searchOrder(String key, String keyType, Pageable page, Integer statusId) {
        Boolean isSearch = null;
        List<Integer> orderIds = null;
        if (key != null && keyType != null) {
            if ((keyType.equals("id") || keyType.equals("shopName"))) {
                isSearch = true;
            } else if (keyType.equals("productName")) {
                orderIds = orderRepo.getOrderIdByProductName("%" + key + "%", shopBean.getAccount().getId());
                if (orderIds.size() < 1) {
                    return new PageList();
                }
            }
        }

        Page<OrderDTO> productList = orderRepo.userOrderSearch(isSearch, key, keyType, orderIds,
                shopBean.getAccount().getId(),
                page, statusId);
        List<com.CloneShopee.DTO.User.OrderResponse.OrderItemDTO> items = orderRepo
                .getOrderItemInListOrderIdsOfUser(productList.getContent().stream()
                        .map(v -> v.getOrderId())
                        .toList());

        Map<Integer, List<com.CloneShopee.DTO.User.OrderResponse.OrderItemDTO>> groupedByOrderId = items.stream()
                .collect(Collectors.groupingBy(com.CloneShopee.DTO.User.OrderResponse.OrderItemDTO::getOrderId));

        productList.getContent().forEach(v -> v.setItems(groupedByOrderId.get(v.getOrderId())));
        PageList pageList = new PageList(productList);

        return pageList;
    }

    public List<OrderItemDTO> generationOrderInListIds(List<Integer> ids, Integer accountId) {
        return cartItemRepo.getInfoCartForAddOrder(ids, accountId);
    }

    public void proccessOrder(ShopItemDTO shopItem, List<OrderItemDTO> items, Map<Integer, PromotionItem> promotions,
            List<Order> orders, Status status, List<OrderItem> orderItems) {
        if (items == null) {
            throw new RuntimeException("Thông tin sản phẩm và của hàng không khớp");
        }
        processOrder(shopItem.getVoucherId(), shopItem, items, promotions, orders, status, orderItems);
    }

    public void processOrder(Integer voucherId, ShopItemDTO shopItem, List<OrderItemDTO> items,
            Map<Integer, PromotionItem> promotions,
            List<Order> orders, Status status, List<OrderItem> orderItems) {
        Order order = new Order();
        order.setStatus(status);
        order.setOrderItems(new ArrayList<>());
        // OrderItem orderItem;
        Double totalAmmount = 0.0;
        PromotionItem promotionItem;
        for (OrderItemDTO item : items) {
            promotionItem = promotions.get(item.getProductVariantId());
            if (promotionItem != null) {
                try {
                    // Trường hợp update thành công khuyến mãi
                    promotionRepo.increaseQuantityUsedOfPromotionItem(promotionItem.getId());
                    item.setPrice(promotionItem.caculatePrice(item.getPrice()));
                } catch (Exception e) { // trường hợp promotionItem hết do xung đột

                }
            }
            totalAmmount += item.getPrice() * item.getQuantity();
            orderItems.add(new OrderItem(item, order));
        }
        order.setTotalAmount(totalAmmount);
        checkVoucher(order, voucherId, shopItem.getVoucherStyle(), shopItem.getShopId(), items);
        order.setNote(shopItem.getMessage());
        order.setShop(new Shop(shopItem.getShopId()));
        order.setAccount(shopBean.getAccount());
        orders.add(order);
    }

    public void checkVoucher(Order order, Integer voucherId, String voucherStyle,
            Integer shopId,
            List<OrderItemDTO> orderItems) {
        VoucherShop voucherShop = null;
        if (voucherId != null) {
            Double totalPriceForDiscount = 0.0;
            voucherShop = getVoucherByType(voucherId, voucherStyle, "token_còn thiếu");
            if (voucherShop != null) {
                VoucherShopAccount voucherAccount = voucherRepository
                        .getVoucherGainedByAccountIdAndvoucherId(shopBean.getAccount().getId(), voucherId);
                if (voucherAccount != null && voucherAccount.getQuantityUsed() < voucherShop.getQuantityPer()
                        && voucherShop.getQuantityUsed() + 1 <= voucherShop
                                .getLimitUsage()) {
                    Set<Integer> productIds = voucherRepository.getVoucherIdIdProduct(voucherId);
                    Double total = 0.0;
                    for (OrderItemDTO v : orderItems) {
                        if (productIds.contains(v.getProductId())) {
                            total += v.getOldPrice() * v.getQuantity();
                            totalPriceForDiscount += v.getPrice() * v.getQuantity();
                        }
                    }
                    if (total > 0) {
                        if (total < voucherShop.getMinimumPurchase()) {
                            throw new RuntimeException(
                                    "Số lượng sản phẩm không đủ so với số lượng voucher yêu cầu ở voucher có id "
                                            + voucherId);
                        }
                        Integer isUpdated = updateOrderQuantityUsed(voucherId, shopBean.getAccount().getId(),
                                voucherShop.getQuantityPer());
                        System.out.println("------ CON SỐ LÀ ------" + isUpdated);
                        if (isUpdated != 2) {
                            throw new RuntimeException(
                                    "Áp dụng voucher id: " + voucherId + " không thành công, vui lòng loại bỏ đi");
                        }
                        voucherShop.canculateOrder(order, totalPriceForDiscount);
                        order.setVoucherShop(voucherShop);
                    } else {
                        throw new RuntimeException(" v");
                    }
                } else {
                    throw new RuntimeException("Bạn chưa nhận hoặc đã quá lược sử dụng voucher có id là " + voucherId
                            + "Vui lòng kiểm tra lại thông tin.");
                }
            } else {
                throw new RuntimeException("Không tìm thấy voucher được yêu cầu, vui lòng kiểm tra lại !");
            }
        }
    }

    public VoucherLive getVoucherByType(Integer voucherId, String stype, String tokenLive) {
        VoucherLive voucher = null;
        if (stype.equals("LIVE")) {
            voucher = voucherRepo.getVoucherLiveByVoucherId(voucherId);
        } else {
            voucher = voucherRepo.getVoucherLiveByVoucherId(voucherId);
        }
        return voucher;

    }

    public void saveOrder(List<Order> orders, List<OrderItem> orderItems) {
        orderRepo.saveAll(orders);
        orderItemRepo.saveAll(orderItems);
    }

    public Integer updateOrderQuantityUsed(Integer voucherId, Integer accountId, Integer quantityPer) {
        return voucherRepository.updateQuantityUsed(voucherId, accountId, quantityPer)
                + voucherRepository.updateQuantityUsedOfVoucherByVoucherId(voucherId);
    }

}
