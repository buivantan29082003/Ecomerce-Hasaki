package com.CloneShopee.services.Common;

import com.CloneShopee.models.Order;
import com.CloneShopee.models.Transaction;
import com.CloneShopee.services.ServiceInterface.PaymentService;

public class PaymentZaloPayService implements PaymentService {

    @Override
    public Object createOrder(Order order) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createOrder'");
    }

    @Override
    public Object payOrder(Order order) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'payOrder'");
    }

    @Override
    public Object refundOrder(Order order, Transaction transaction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'refundOrder'");
    }

    @Override
    public Object getPaymentInfo(Order order) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPaymentInfo'");
    }

}
