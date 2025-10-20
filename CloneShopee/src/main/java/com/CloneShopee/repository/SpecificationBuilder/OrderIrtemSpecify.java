package com.CloneShopee.repository.SpecificationBuilder;

import org.springframework.data.jpa.domain.Specification;

import com.CloneShopee.models.Order;

public class OrderIrtemSpecify {
    public static Specification<Order> hasStatus(Integer statusId) {
        return (root, query, criteriaBuilder) -> {
            if (statusId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status").get("id"), statusId);
        };
    }

}