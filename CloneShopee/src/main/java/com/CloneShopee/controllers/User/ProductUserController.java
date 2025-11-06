package com.CloneShopee.controllers.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.CloneShopee.DTO.User.product.ProductDetail;
import com.CloneShopee.DTO.User.product.ProductInfo;
import com.CloneShopee.DTO.User.product.ProductReviewDTO;
import com.CloneShopee.DTO.User.product.SaleItem;
import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.ResponeEntity.PageList;
import com.CloneShopee.models.Product;
import com.CloneShopee.services.User.CategoryService;
import com.CloneShopee.services.User.ProductReviewService;
import com.CloneShopee.services.User.ProductServiceUser;
import com.CloneShopee.services.sale.ProductService;

@RestController
@CrossOrigin("*")
public class ProductUserController {

    @Autowired
    ProductService productService;
    @Autowired
    ProductServiceUser productServiceUser;
    @Autowired
    ProductReviewService productReviewService;

    @Autowired
    CategoryService cateService;

    @GetMapping("/commond/product/recommand_cate")
    public ResponseEntity<Object> getRecommendByCategoryId(@RequestParam("categoryIds") List<Integer> categoryIds) {
        List<Integer> categorys = cateService.getAllIdChildInCategoryId(categoryIds);
        categorys.addAll(categoryIds);
        categorys.forEach(v -> System.out.println("----- " + v));
        return productSearch(
                null, // keyword
                null, // minPrice
                null, // maxPrice
                categorys, // chỉ truyền categoryIds
                null, // brandIds
                null, // propertyValueIds
                0, // typeSort (default)
                0 // page (default)
        );
    }

    @GetMapping("/common/product/productvariants")
    public ResponseEntity<Object> getProductVariantByProductId(
            @RequestParam(value = "productId", defaultValue = "-1") Integer productId) {
        return new ResponseEntity<>(
                new BaseRespone(productServiceUser.getAllProductVariantOfProduct(productId), "success"), HttpStatus.OK);
    }

    @GetMapping("/common/shop/vouchers")
    public ResponseEntity<Object> getAllVoucherInProducts(@RequestParam("shopId") Integer shopId) {
        return new ResponseEntity<>(new BaseRespone(productService.getALlVoucherOfShop(shopId), "success"),
                HttpStatus.OK);
    }

    @GetMapping("/common/product/reviews")
    public ResponseEntity<Object> getReviews(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "isOveral", required = false) Integer isOveral,
            @RequestParam(value = "productId", required = false, defaultValue = "0") Integer productId) {
        Map<String, Object> data = new HashMap();
        if (isOveral != null) {
            Map<Integer, Integer> m = new HashMap<>();
            for (int i = 1; i <= 5; i++) {
                m.put(i, 0);
            }
            List<Object[]> objs = productReviewService.getGroupStar(productId);
            objs.forEach(v -> {
                m.put(Integer.parseInt(v[0] + ""), Integer.parseInt(v[1] + ""));
            });
            data.put("overal", m);
        }
        Pageable p = PageRequest.of(page, 5);
        Page<ProductReviewDTO> productReviews = productReviewService.getReviews(productId, p);
        PageList pa = new PageList(productReviews.getTotalPages(), productReviews.getTotalElements(),
                productReviews.getContent());
        data.put("reviews", pa);
        return new ResponseEntity<>(new BaseRespone(data, "success"), HttpStatus.OK);
    }

    @GetMapping("common/product/detail")
    public ResponseEntity<Object> getProductDetail(@RequestParam("productId") Integer productId) {
        Product product = productService.getProductById(productId);
        if (product.getStatus() != 1) {
            return new ResponseEntity<>(new BaseRespone(null, "Sản phẩm hiện tại không mở bán"),
                    HttpStatus.BAD_REQUEST);
        }
        ProductDetail p = new ProductDetail(product);
        p.setPromotions(productServiceUser.getPromotionOfProductByProductId(productId));
        p.setVouchers(productServiceUser.getVoucherOfProductByProductId(productId));
        p.setProductImages(productServiceUser.getProductImageByProductId(productId));
        p.setPropertyItems(productServiceUser.getPropertyByProductId(productId));
        return new ResponseEntity<>(new BaseRespone(p, "successs"), HttpStatus.OK);
    }

    @GetMapping("common/product/topsaleofshop")
    public ResponseEntity<Object> getTopSaleOfShop(
            @RequestParam(value = "shopId", required = false, defaultValue = "0") Integer shopId) {
        Pageable pageable = PageRequest.of(
                0,
                10,
                Sort.by(
                        Sort.Order.desc("totalSale")));
        Page<ProductInfo> productList = productServiceUser.getTopSaleOfShop(pageable, shopId);
        List<Integer> productIds = productList.getContent().stream().map(v -> v.getProductId()).toList();
        List<SaleItem> saleItems = productServiceUser.getSaleInList(productIds);
        Map<Integer, SaleItem> saleItemMap = new HashMap<>();
        for (SaleItem item : saleItems) {
            saleItemMap.put(item.getProductId(), item);
        }
        productList.forEach(v -> {
            v.setSale(saleItemMap.get(v.getProductId()));
        });
        PageList p = new PageList(productList.getTotalPages(), productList.getTotalElements(),
                productList.getContent());
        return new ResponseEntity<>(new BaseRespone(p, "success"), HttpStatus.OK);
    }

    @GetMapping("user/product/getall")
    public ResponseEntity<Object> getAllProducts(
            @RequestParam(name = "productId", defaultValue = "-1") Integer productId) {
        return new ResponseEntity<>(productService.findAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/common/product/onsale")
    private ResponseEntity<Object> getProductOnSale() {

        return new ResponseEntity<>(new BaseRespone(productServiceUser.getProductOnSale(), null), HttpStatus.OK);
    }

    @GetMapping("/common/product/new")
    public ResponseEntity<Object> getProductNew(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page) {
        if (page < 0) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(
                page,
                20,
                Sort.by(
                        Sort.Order.desc("id")));
        Page<ProductInfo> productList = productServiceUser.getProductNew(pageable);
        List<Integer> productIds = productList.getContent().stream().map(v -> v.getProductId()).toList();
        List<SaleItem> saleItems = productServiceUser.getSaleInList(productIds);
        Map<Integer, SaleItem> saleItemMap = new HashMap<>();
        for (SaleItem item : saleItems) {
            saleItemMap.put(item.getProductId(), item);
        }
        productList.forEach(v -> {
            v.setSale(saleItemMap.get(v.getProductId()));
        });
        PageList p = new PageList(productList.getTotalPages(), productList.getTotalElements(),
                productList.getContent());

        return new ResponseEntity<>(new BaseRespone(p, "success"), HttpStatus.OK);
    }

    Map<Integer, String> typeSort = new HashMap<>();

    public ProductUserController() {
        typeSort.put(0, "id");
        typeSort.put(1, "id");
        typeSort.put(2, "id");
        typeSort.put(3, "id");
        typeSort.put(4, "id");
    }

    @GetMapping("common/product/search")
    public ResponseEntity<Object> productSearch(@RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "categoryId", required = false) List<Integer> categoryIds,
            @RequestParam(value = "brandId", required = false) List<Integer> brandIds,
            @RequestParam(value = "propertyValueIds", required = false) List<Integer> propertyValueIds,
            @RequestParam(value = "typeSort", required = false, defaultValue = "0") Integer typeSort,
            @RequestParam(value = "page", defaultValue = "0", required = true) Integer page) {
        Sort sort;
        if (typeSort == 1) {
            sort = Sort.by(Sort.Direction.DESC, "countStart");
        } else if (typeSort == 2) {
            sort = Sort.by(Sort.Direction.DESC, "totalSale");
        } else if (typeSort == 3) {
            sort = Sort.by(Sort.Direction.ASC, "minPrice");
        } else if (typeSort == 4) {
            sort = Sort.by(Sort.Direction.DESC, "minPrice");
        } else {
            sort = Sort.by(Sort.Direction.DESC, "id");
        }
        Pageable pageable = PageRequest.of(page, 20, sort);
        Page<Product> products = productServiceUser.filterProduct(keyword, categoryIds, brandIds, minPrice, maxPrice,
                propertyValueIds, pageable);
        List<Integer> productIds = products.getContent().stream().map(v -> v.getId()).toList();
        List<SaleItem> saleItems = productIds.size() > 0 ? productServiceUser.getSaleInList(productIds)
                : new ArrayList<>();
        Map<Integer, SaleItem> saleItemMap = new HashMap<>();
        for (SaleItem item : saleItems) {
            saleItemMap.put(item.getProductId(), item);
        }
        List<ProductInfo> pCurrent = products.getContent().stream().map(v -> {
            return new ProductInfo(v);
        }).toList();
        pCurrent.forEach(v -> {
            v.setSale(saleItemMap.get(v.getProductId()));
        });
        return new ResponseEntity<>(
                new BaseRespone(new PageList(products.getTotalPages(), products.getTotalElements(), pCurrent), ""),
                HttpStatus.OK);
    }

    // @GetMapping("/user/product/filter")
    // public ResponseEntity<Object> filterProduct(
    // @RequestParam(name = "name", required = false) String name,
    // @RequestParam(name = "status", required = false) Integer status,
    // @RequestParam(name = "minPrice", required = false) Double minPrice,
    // @RequestParam(name = "maxPrice", required = false) Double maxPrice,
    // @RequestParam(value = "categoryIds", required = false) List<Integer>
    // categoryIds,
    // @RequestParam(value = "brandIds", required = false) List<Integer> brandIds) {
    // List<Product> products = productService.filterProducts(name, categoryIds,
    // status, brandIds, minPrice, maxPrice);
    // List<ProductSearch> productSearchs = products.stream().map(v -> new
    // ProductSearch(v.getId(), v.getShop(),
    // v.getProductName(), v.getMinPrice(), v.getMaxPrice(),
    // v.getProductImage())).toList();
    // List<PromotionInProductSearch> promotions =
    // productService.getPromotionInfoOfProduct(products);
    // Map<Integer, List<PromotionInProductSearch>> promotionMap = new HashMap<>();
    // for (PromotionInProductSearch promo : promotions) {
    // promotionMap.computeIfAbsent(promo.getProductId(), k -> new
    // ArrayList<>()).add(promo);
    // }
    // for (ProductSearch product : productSearchs) {
    // product.setPromotions(promotionMap.getOrDefault(product.getId(),
    // Collections.emptyList()));
    // }
    // return new ResponseEntity<>(new BaseRespone(productSearchs, "success"),
    // HttpStatus.OK);
    // }

    @GetMapping("/user/product/detail")
    public ResponseEntity<Object> getInffoDetail(
            @RequestParam(name = "productId", required = false) Integer productId) {
        Product product = productService.findByIdFullProperties(productId);
        if (product != null) {
            Map<String, Object> productInfo = new HashMap<>();
            productInfo.put("product", product);
            productInfo.put("promotion", productService.getPromotionOfProductId(productId));
            return new ResponseEntity(new BaseRespone(productInfo, "success"), HttpStatus.OK);
        }
        return new ResponseEntity(new BaseRespone(null, "Not find product"), HttpStatus.OK);
    }

}
