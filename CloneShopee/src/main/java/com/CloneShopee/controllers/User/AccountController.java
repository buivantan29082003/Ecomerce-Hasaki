package com.CloneShopee.controllers.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CloneShopee.Bean.ShopBean;
import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.services.Common.AccountService;

@RestController
@CrossOrigin("*")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    ShopBean shopBean;

    @GetMapping("/user/account/info")
    public ResponseEntity<Object> getAccountById() {
        return new ResponseEntity<>(
                new BaseRespone(accountService.getAccountById(shopBean.getAccount().getId()), "success"),
                HttpStatus.OK);
    }

}
