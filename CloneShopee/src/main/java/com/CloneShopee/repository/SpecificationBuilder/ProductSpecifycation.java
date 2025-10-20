// package com.CloneShopee.repository.SpecificationBuilder;

// // import org.springframework.data.jpa.domain.Specification;
// // import com.CloneShopee.models.Product;
// // import com.CloneShopee.models.Shop;

// // import jakarta.persistence.criteria.*;

// // public class ProductSpecifycation {
// // public static Specification<Product> filterByShop(Integer shopId) {
// // return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
// {
// // Join<Product, Shop> shopJoin = root.join("shop", JoinType.INNER); // JOIN
// với
// // Shop
// // return cb.equal(shopJoin.get("id"), shopId);
// // };
// // }
// // }
