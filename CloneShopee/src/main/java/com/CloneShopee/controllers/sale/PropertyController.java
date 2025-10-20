package com.CloneShopee.controllers.sale;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.services.sale.PropertyService;

@RestController
@CrossOrigin("*")
public class PropertyController {
    @Autowired
    PropertyService propertyService;

    @GetMapping("sale/properties/getall")
    public ResponseEntity<Object> getAllCategories(@RequestParam(name = "categoryId", required = false) Integer id) {
        if (id == null) {
            return new ResponseEntity<>(new BaseRespone(new ArrayList<>(), "success"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseRespone(propertyService.getByCategoryId(id), "success"), HttpStatus.OK);
    }

}
