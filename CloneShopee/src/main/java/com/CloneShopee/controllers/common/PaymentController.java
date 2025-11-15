package com.CloneShopee.controllers.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.services.Common.PaymentService;

@RestController
@CrossOrigin("*")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    private List<PaymentMethod> paymentMethods = new ArrayList<>();

    public PaymentController() {
        PaymentMethod p = new PaymentMethod("VNPAY", "Vnpay Việt Nam",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS7Ac_hf1yUnDUcjRCNKOlfdjlt2-HgVKhGAZmFANPKpg&s");
        paymentMethods.add(p);
    }

    @GetMapping("/common/payments")
    public ResponseEntity<Object> getPayments() {
        return new ResponseEntity<>(new BaseRespone(paymentService.getAllPayment(), "success"), HttpStatus.OK);
    }

    @GetMapping("/common/paymentmethods")
    public ResponseEntity<Object> getAllPaymentMethod() {
        return new ResponseEntity<>(new BaseRespone(paymentMethods, "success"), HttpStatus.OK);
    }

}

class PaymentMethod {
    private String paymentCode;
    private String paymentName;
    private String logo;

    public PaymentMethod(String paymentCode, String paymentName, String logo) {
        this.paymentCode = paymentCode;
        this.paymentName = paymentName;
        this.logo = logo;
    }

    public PaymentMethod() {
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

}