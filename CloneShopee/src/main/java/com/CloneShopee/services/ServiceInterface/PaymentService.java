package com.CloneShopee.services.ServiceInterface;

import com.CloneShopee.models.Order;
import com.CloneShopee.models.Transaction;

public interface PaymentService {
    public Object createOrder(Order order);

    public Object payOrder(Order order);

    public Object refundOrder(Order order, Transaction transaction);

    public Object getPaymentInfo(Order order);

}
