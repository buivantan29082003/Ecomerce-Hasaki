package com.CloneShopee.services.Common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CloneShopee.repository.PaymentRepository;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepo;

    public List<com.CloneShopee.models.Payment> getAllPayment() {
        return paymentRepo.findAll();
    }
}
