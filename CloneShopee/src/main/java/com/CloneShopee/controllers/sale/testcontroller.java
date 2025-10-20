package com.CloneShopee.controllers.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CloneShopee.repository.VoucherRepository;

@RestController

public class testcontroller {

    @Autowired
    VoucherRepository voucherRepo;

    @GetMapping("/test1")
    public Object getVoucher() {
        return voucherRepo.findAll();
    }
}
