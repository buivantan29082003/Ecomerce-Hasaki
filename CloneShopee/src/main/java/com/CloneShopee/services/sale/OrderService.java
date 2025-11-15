package com.CloneShopee.services.sale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.CloneShopee.Bean.ShopBean;
import com.CloneShopee.DTO.Sale.OrderItemDTO;
import com.CloneShopee.DTO.User.OrderResponse.OrderDTO;
import com.CloneShopee.ExceptionGlobal.ConstraintException;
import com.CloneShopee.ResponeEntity.PageList;
import com.CloneShopee.models.Order;
import com.CloneShopee.models.Transaction;
import com.CloneShopee.repository.OrderItemRepository;
import com.CloneShopee.repository.OrderRepository;
import com.CloneShopee.services.Common.PaymentVNPayService;

@Service
public class OrderService {

    public static final Map<String, StatusCustome> statusCode = Map.of("UNPAID", new StatusCustome(1, new HashMap<>()),
            "TO_SHIP", new StatusCustome(2, Map.of("TO_PROCCESS", "Đang xử lý", "PROCCESSED", "Đã xử lý")),
            "SHIPPING", new StatusCustome(3, new HashMap<>()),
            "COMPLETE", new StatusCustome(4, new HashMap<>()),
            "SHIPPING_FAIL",
            new StatusCustome(5, Map.of("RETURNING", "Đang trả hàng", "RETURNED", "Đã trả hàng", "LOST", "Thất lạc")),
            "CANCEL", new StatusCustome(6,
                    Map.of("TO_PROCCESS", "Đang xử lý", "RETURNED", "Đã hoàn tiền", "PROCCESSED", "Đã xử lý")));

    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    OrderItemRepository orderItemRepo;

    @Autowired
    ShopBean shopBean;

    @Autowired
    PaymentVNPayService vnPayService;

    private final Integer statusCancel = 9;
    private final Integer paymentOnline = 3;

    public void updateStatusOrder(Integer orderId, Integer statusId) {
        orderRepo.updateStatusOfOrder(orderId, statusId);
    }

    public void cancelOrder(Integer orderId, String reasonCancel) {
        orderRepo.cancelOrder(orderId, 14, reasonCancel);
    }

    public Boolean refundOrder(Order order, Transaction tran) {
        if (tran.getMethodCode().equals("VNPAY")) {
            return (Boolean) vnPayService.refundOrder(order, tran);
        }
        return false;
    }

    public Order getOrderByShopId(Integer orderId) {
        return orderRepo.getOrderByOrderIdAndShopId(orderId, shopBean.getShop().getId());
    }

    public Object getAllOrder() {
        return orderRepo.getOrderInfos();
    }

    public PageList searchOrder(String key, String keyType, Pageable page, Integer statusId, Integer paymentMethod) {
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
        Page<com.CloneShopee.DTO.Sale.OrderResponse.OrderDTO> productList = orderRepo.userOrderSearch(isSearch, key,
                keyType, orderIds,
                shopBean.getAccount().getId(),
                page, statusId, paymentMethod);
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

    public Boolean checkOrderOfShop(Integer orderId, Integer shopId) {
        return orderRepo.checkOrderOfShop(orderId, shopId).orElse(-1) != -1;
    }

    public Boolean checkOrderOfShop(Integer orderId, Integer shopId, Integer statusId) {
        return orderRepo.checkOrderOfShopAndStatusId(orderId, shopId, statusId).orElse(-1) != -1;
    }

    public List<OrderItemDTO> getOrderItemByOrderId(Integer orderId, Integer shopId) {
        if (checkOrderOfShop(orderId, shopId)) {
            return orderRepo.getOrderItemsByOrderId(orderId);
        }
        throw new ConstraintException("order", "Order not of shop");
    }

    public void checkStatusOrdersAndOfShop(Set<Integer> orderIds, Integer shopId, Integer statusId) {
        Integer countOrderIds = orderRepo.countOrderByStatusAndOfShop(orderIds, statusId, shopId);
        if (countOrderIds != orderIds.size()) {
            throw new ConstraintException("orders", "Order or status is Not valid");
        }
    }

    public void updateStatusAndTagOrder(Set<Integer> orderIds, Integer statusId, String tag) {
        orderRepo.updateStatusAndTagOrders(orderIds, tag, statusId);
    }

    public void updateStatusAndTagOrder(Integer orderId, Integer statusId, String tag) {
        orderRepo.updateStatusAndTagOrders(orderId, tag, statusId);
    }

}
