package com.CloneShopee.services.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.CloneShopee.DTO.User.product.ProductReviewDTO;
import com.CloneShopee.repository.ProductReviewRepository;

@Service
public class ProductReviewService {
    @Autowired
    ProductReviewRepository productReviewRepo;

    public List<Object[]> getGroupStar(Integer productId) {
        return productReviewRepo.getGroupStar(productId);
    }

    public Page<ProductReviewDTO> getReviews(Integer productId, Pageable page) {
        return productReviewRepo.getReviews(productId, page);
    }

}
