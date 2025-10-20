package com.CloneShopee.controllers.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.repository.BrandRepository;

@RestController
@CrossOrigin("*")
public class BrandController {
    @Autowired
    BrandRepository brandRepository;

    @GetMapping("sale/brand/getall")
    public ResponseEntity<Object> getAllCategories() {
        System.out.println("Hello World");
        return new ResponseEntity<>(new BaseRespone(brandRepository.findAll(), "success"), HttpStatus.OK);
    }
}
