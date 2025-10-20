package com.CloneShopee.repository.SpecificationBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import com.CloneShopee.models.VoucherShop;

import jakarta.persistence.criteria.Predicate;

public class VoucherSelerSpec {
    public static Specification<VoucherShop> filterProducts(String key, Integer status) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // chỉ lấy entity cha, bỏ join subclasses
            predicates.add(criteriaBuilder.equal(root.type(), VoucherShop.class));

            if (key != null && !key.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("voucherName"), "%" + key + "%"));
            }

            if (status != null) {
                LocalDateTime today = LocalDateTime.now();
                switch (status) {
                    case 0:
                        break;

                    case 1: // Đang hiệu lực
                        predicates.add(
                                criteriaBuilder.between(
                                        criteriaBuilder.literal(today),
                                        root.get("startDate"),
                                        root.get("endDate")));
                        break;

                    case 2: // Sắp diễn ra (trong 24h)
                        LocalDateTime next24h = today.plusHours(24);
                        predicates.add(
                                criteriaBuilder.between(
                                        root.get("startDate"),
                                        criteriaBuilder.literal(today),
                                        criteriaBuilder.literal(next24h)));
                        break;

                    case 3: // Ngừng hoạt động
                        predicates.add(criteriaBuilder.equal(root.get("isActive"), 0));
                        break;

                    case 4: // Hết hạn
                        predicates.add(criteriaBuilder.lessThan(root.get("endDate"), today));
                        break;
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
