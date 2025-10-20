package com.CloneShopee.services.sale;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CloneShopee.models.Category;
import com.CloneShopee.repository.Categoryrepository;

@Service
public class CategorySerice {
    @Autowired
    Categoryrepository categoryRepo;

    public List<Category> getCategories() {
        return categoryRepo.findAllParentCategories();
    }

    public List<Category> getCategoriesWithNotChile() {
        return categoryRepo.findAllParentCategoriesWithEmptyChild();
    }

}
