package com.CloneShopee.services.Common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CloneShopee.models.Transaction;
import com.CloneShopee.repository.TransactionRepository;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepo;
    @Autowired
    PaymentVNPayService vnpay;
    PaymentZaloPayService zalopay;

    public Boolean createTransaction(Transaction tran, String methodCode) {
        try {
            tran.setTransactionStatus(0);
            if (methodCode.equals("VNPAY")) {
                tran.setMethodCode("VNPAY");
                Object a = vnpay.createOrder(tran.getOrder());
                tran.setTransactionLink((String) a);
            } else {
                return false;
            }
            transactionRepo.save(tran);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void saveTransaction(Transaction p) {
        transactionRepo.save(p);
    }

    public Transaction getTransactionByOrderId(Integer orderId) {
        return transactionRepo.getTransactionByOrderId(orderId);
    }

}
