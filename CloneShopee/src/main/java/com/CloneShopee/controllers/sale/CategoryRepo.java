package com.CloneShopee.controllers.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.services.sale.CategorySerice;

@RestController
@CrossOrigin("*")
public class CategoryRepo {

    @Autowired
    CategorySerice categoryService;

    @GetMapping("sale/categories/getall")
    public ResponseEntity<Object> getAllCategories() {
        return new ResponseEntity<>(new BaseRespone(categoryService.getCategories(), "success"), HttpStatus.OK);
    }

    @GetMapping("common/categories/getall/notchild")
    public ResponseEntity<Object> getAllCategoriesWithNotChild() {
        return new ResponseEntity<>(new BaseRespone(categoryService.getCategoriesWithNotChile(), "success"),
                HttpStatus.OK);
    }
}
