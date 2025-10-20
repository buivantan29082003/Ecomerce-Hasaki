package com.CloneShopee.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.ResponeEntity.LoginData;
import com.CloneShopee.Utils.TokenUtil;
import com.CloneShopee.config.Base64PasswordService;
import com.CloneShopee.services.AuthService;

@RestController
public class AuthController {

    @Autowired
    AuthService auth;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    Base64PasswordService base64Service;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginData loginData) {
        Integer userId = auth.getUserByEmail(loginData.getEmail(), base64Service.encode(loginData.getPassword()));
        if (userId != null) {
            String token = tokenUtil.generateToken(userId + "");
            return new ResponseEntity<>(new BaseRespone(token, "success"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseRespone(null, "Email hoặc password không chính xác"),
                HttpStatus.BAD_REQUEST);
    }

}
