package com.CloneShopee.controllers.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.CloneShopee.Bean.ShopBean;
import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.models.Address;
import com.CloneShopee.services.User.AddressService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
public class AddressController {

    @Autowired
    AddressService addressService;

    @Autowired
    ShopBean shopBean;

    @Transactional
    @PostMapping("/user/address/add")
    public ResponseEntity<Object> add(@RequestBody @Valid Address v) {

        v.setAccount(shopBean.getAccount());
        v.setId(null);
        v.setShop(null);
        Integer isSuccess = addressService.addAddress(v);
        if (isSuccess == 1) {
            return new ResponseEntity<>(new BaseRespone(null, "success"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseRespone(null, "error with insert address"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/user/address/all")
    public ResponseEntity<Object> getAllAddressOfUser() {
        return new ResponseEntity<>(new BaseRespone(addressService.getAllAddressOfUser(), "success"), HttpStatus.OK);
    }
}