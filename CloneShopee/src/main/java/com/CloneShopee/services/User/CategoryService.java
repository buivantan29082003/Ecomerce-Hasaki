package com.CloneShopee.services.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CloneShopee.repository.Categoryrepository;

@Service
public class CategoryService {

    @Autowired
    Categoryrepository cateRepo;

    public List<Integer> getAllIdChildInCategoryId(List<Integer> c) {
        return cateRepo.findAllChildrenRecursive(c);
    }
}
