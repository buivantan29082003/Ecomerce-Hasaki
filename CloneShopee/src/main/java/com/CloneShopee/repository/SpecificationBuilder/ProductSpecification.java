package com.CloneShopee.repository.SpecificationBuilder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.CloneShopee.models.Product;
import jakarta.persistence.criteria.*;

public class ProductSpecification {

    public static Specification<Product> filterProductsWithJoinfetch(
            String name, List<Integer> categoryIds, Integer status,
            List<Integer> brandIds, Double minPrice, Double maxPrice, List<Integer> ids, Integer shopId,
            List<Integer> productIds) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (productIds != null) {
                predicates.add(root.get("id").in(productIds));
            }

            if (shopId != null) {
                predicates.add(criteriaBuilder.equal(root.get("shop").get("id"), shopId));
            }

            // if (brandIds != null && !brandIds.isEmpty()) {
            // predicates.add(root.get("brand").get("id").in(brandIds));
            // }
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("productName"), "%" + name + "%"));
            }

            if (ids != null) {
                predicates.add(root.get("id").in(ids));
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

    public static Specification<Product> filterProductsWithJoinfetchNotIn(
            String name, List<Integer> categoryIds, Integer status,
            List<Integer> brandIds, Double minPrice, Double maxPrice, Integer shopId,
            List<Integer> productIdsNotIn) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (productIdsNotIn != null) {
                predicates.add(root.get("id").in(productIdsNotIn).not());
            }

            if (shopId != null) {
                predicates.add(criteriaBuilder.equal(root.get("shop").get("id"), shopId));
            }

            // if (brandIds != null && !brandIds.isEmpty()) {
            // predicates.add(root.get("brand").get("id").in(brandIds));
            // }
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("productName"), "%" + name + "%"));
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

    public static Specification<Product> filterProducts(String name, List<Integer> categoryIds, Integer status) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("productName"), "%" + name + "%"));
            }
            if (categoryIds != null) {
                predicates.add(root.get("category").get("id").in(categoryIds));
            }
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
