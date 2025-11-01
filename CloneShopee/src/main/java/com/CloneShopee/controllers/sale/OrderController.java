package com.CloneShopee.controllers.sale;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CloneShopee.Bean.ShopBean;
import com.CloneShopee.ExceptionGlobal.ConstraintException;
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

    @GetMapping("sale/order/findall")
    public ResponseEntity<Object> getAllOrder() {
        return new ResponseEntity<>(orderService.getAllOrder(), HttpStatus.OK);
    }

    @GetMapping("sale/order/finditems")
    public ResponseEntity<Object> getAllOrder(@RequestParam(name = "orderId", defaultValue = "-1") Integer orderId) {
        return new ResponseEntity<>(orderService.getOrderItemByOrderId(orderId, shopBean.getShop().getId()),
                HttpStatus.OK);
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
