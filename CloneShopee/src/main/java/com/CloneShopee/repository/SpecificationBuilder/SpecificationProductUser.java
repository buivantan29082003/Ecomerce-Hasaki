package com.CloneShopee.repository.SpecificationBuilder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.CloneShopee.DTO.User.product.ProductInfo;
import com.CloneShopee.models.Product;

import jakarta.persistence.criteria.Predicate;

public class SpecificationProductUser {
    public static Specification<Product> filterProducts(
            String keyWord, List<Integer> categoryIds, List<Integer> brandIds, Double minPrice, Double maxPrice,
            List<Integer> productIds, Boolean isInProduct) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("status"), 1));
            if (isInProduct) {
                if (productIds.isEmpty()) {
                    productIds.add(-1);
                }
                predicates.add(root.get("id").in(productIds));
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }

            if (keyWord != null && !keyWord.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("productName"), "%" + keyWord + "%"));
            }

            if (brandIds != null && !brandIds.isEmpty()) {
                predicates.add(root.get("brand").get("id").in(brandIds));
            }

            if (categoryIds != null && !categoryIds.isEmpty()) {
                predicates.add(root.get("category").get("id").in(categoryIds));
            }
            if (minPrice != null && maxPrice != null && minPrice < maxPrice) {
                Predicate minPricePredicate = criteriaBuilder.between(root.get("minPrice"), minPrice, maxPrice);
                Predicate maxPricePredicate = criteriaBuilder.between(root.get("maxPrice"), minPrice, maxPrice);
                predicates.add(criteriaBuilder.or(minPricePredicate, maxPricePredicate));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
