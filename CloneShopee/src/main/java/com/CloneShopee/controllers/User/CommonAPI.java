package com.CloneShopee.controllers.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.models.Category;
import com.CloneShopee.repository.Categoryrepository;
import com.CloneShopee.services.User.CommonService;

@RestController
@CrossOrigin("*")
public class CommonAPI {

    @Autowired
    CommonService commonService;

    @Autowired
    Categoryrepository caterepo;

    @GetMapping("/common/recommendproperty")
    public ResponseEntity<Object> getAllPropertyByCategoryId(@Param("categoryId") Integer categoryId) {
        return new ResponseEntity<>(
                new BaseRespone(commonService.getAllPropertyRecommendByCateId(categoryId), "success"), HttpStatus.OK);
    }

    @GetMapping("/common/recommendbrand")
    public ResponseEntity<Object> getRecommendBrandByCategoryIds(
            @RequestParam(value = "categoryIds", required = true) List<Integer> cateIds) {
        return new ResponseEntity<>(new BaseRespone(commonService.getRecommentBrandByCategoryId(cateIds), "success"),
                HttpStatus.OK);
    }

    @GetMapping("/common/brandlist")
    public ResponseEntity<Object> getBrandList(
            @RequestParam(value = "categoryId", required = true) Integer cateId) {
        List<CateList> cates = new ArrayList<>();
        Category c = caterepo.getCateById(cateId);
        while (c != null) {
            cates.add(new CateList(c.getId(), c.getCategoryName()));
            c = c.getParent();
        }
        return new ResponseEntity<>(new BaseRespone(cates.reversed(), "success"),
                HttpStatus.OK);
    }

}

class CateList {
    private Integer id;
    private String categoryName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CateList(Integer id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public CateList() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}