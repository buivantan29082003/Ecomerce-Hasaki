package com.CloneShopee.services.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.CloneShopee.DTO.User.product.ProductInfo;
import com.CloneShopee.DTO.User.product.ProductOnsale;
import com.CloneShopee.DTO.User.product.PropertyItem;
import com.CloneShopee.DTO.User.product.SaleDetail;
import com.CloneShopee.DTO.User.product.SaleItem;
import com.CloneShopee.DTO.User.product.VoucherDetail;
import com.CloneShopee.models.Product;
import com.CloneShopee.models.ProductVariant;
import com.CloneShopee.models.PromotionItem;
import com.CloneShopee.repository.Categoryrepository;
import com.CloneShopee.repository.ProductImagesReopository;
import com.CloneShopee.repository.ProductRepository;
import com.CloneShopee.repository.PromotionRepository;
import com.CloneShopee.repository.VoucherRepository;
import com.CloneShopee.repository.SpecificationBuilder.SpecificationProductUser;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Service
public class ProductServiceUser {

    @Autowired
    ProductRepository productRepo;

    @Autowired
    PromotionRepository promotionRepo;

    @Autowired
    VoucherRepository voucherRepo;

    @Autowired
    ProductImagesReopository productImageRepo;

    @Autowired
    Categoryrepository cateRepo;

  

    public Integer getQuantityOfProductIsValidToOrder(Integer productId) {
        return productRepo.getQuantityProductIsValidForOrder(productId);
    }

    public ProductVariant getProductVariantById(Integer productVariantId) {
        return productRepo.getProductVariantByProductVariantId(productVariantId);
    }

    public List<ProductVariant> getAllProductVariantOfProduct(Integer productId) {
        return productRepo.getAllProductVarinatByProductId(productId);
    }

    public List<Integer> getProductIdHavePropertyValues(List<Integer> propertyValueIds) {
        return productRepo.getProductByPropertyIdValues(propertyValueIds, propertyValueIds.size());
    }

    public List<PromotionItem> getAllPromotionByProductVariantIds(List<Integer> productVariantIds) {
        return promotionRepo.getAllPromotionOfVariants(productVariantIds);
    }

    public List<Object> getPromotionOfVariants(List<Integer> productVariantIds) {

        return null;
    }

    public Page<Product> filterProduct(String keyword, List<Integer> categoryIds, List<Integer> brandIds,
            Double minPrice, Double maxPrice, List<Integer> propertyValueIds, Pageable page) {
        List<Integer> a = propertyValueIds != null ? getProductIdHavePropertyValues(propertyValueIds)
                : new ArrayList<>();
        Specification<Product> specification = SpecificationProductUser.filterProducts(keyword, categoryIds, brandIds,
                minPrice, maxPrice, a, propertyValueIds != null);
        return productRepo.findAll(specification, page);
    }

    public Page<ProductInfo> getTopSaleOfShop(Pageable p, Integer shopId) {
        return productRepo.getTopSaleOfShop(p, shopId);
    }

    public List<PropertyItem> getPropertyByProductId(Integer productId) {
        return productRepo.getPropertyByProductId(productId);
    }

    public SaleDetail getPromotionOfProductByProductId(Integer productId) {
        return promotionRepo.getAllPromotionOfProduct(productId);
    }

    public List<String> getProductImageByProductId(Integer productId) {
        return productImageRepo.getProductImageLinkByProductId(productId);
    }

    public List<VoucherDetail> getVoucherOfProductByProductId(Integer productId) {
        return voucherRepo.getVoucherDetailByProductId(productId);
    }

    public Product getProductById(Integer productId) {
        return productRepo.getById(productId);
    }

    public List<ProductOnsale> getProductOnSale() {
        return productRepo.getProductOnsale();
    }

    public List<SaleItem> getSaleInList(List<Integer> productIds) {
        return productRepo.getSaleInProduct(productIds);
    }

    public Page<ProductInfo> getProductNew(Pageable p) {
        return productRepo.getProductNew(p);
    }

    public Boolean isSameProduct(Integer productId1, Integer productId2, Integer productId) {
        return productRepo.checkVariantSameProduct(productId1, productId2, productId) == 2;
    }
}
