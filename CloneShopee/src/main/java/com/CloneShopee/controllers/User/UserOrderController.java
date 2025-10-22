package com.CloneShopee.controllers.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.CloneShopee.Bean.ShopBean;
import com.CloneShopee.DTO.User.OrderInsertDTO;
import com.CloneShopee.DTO.User.OrderItemDTO;
import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.models.Address;
import com.CloneShopee.models.Order;
import com.CloneShopee.models.OrderItem;
import com.CloneShopee.models.PromotionItem;
import com.CloneShopee.models.Status;
import com.CloneShopee.services.User.AddressService;
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
    ProductServiceUser productServiceUser;

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

            // tạo danh sách các order mẫu
            List<Order> orders = new ArrayList<>();
            // hình thức online
            Status status = new Status(1);
            List<OrderItem> orderItemss = new ArrayList<>();
            order.getShops().forEach(v -> {
                orderService.proccessOrder(v, mapShop.get(v.getShopId()), groupedByProduct, orders, status,
                        orderItemss);
            });
            // orderService.saveOrder(orders, orderItemss);
            return new ResponseEntity<>(new BaseRespone(null, "success"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseRespone(null, "Danh sách sản phẩm yêu cầu không hợp lệ"),
                HttpStatus.BAD_REQUEST);
    }

}