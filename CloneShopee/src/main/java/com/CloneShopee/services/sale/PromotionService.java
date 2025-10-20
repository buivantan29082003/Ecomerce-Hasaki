package com.CloneShopee.services.sale;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.CloneShopee.Bean.ShopBean;
import com.CloneShopee.DTO.Sale.Promotion.PromotionInfo;
import com.CloneShopee.DTO.Sale.Promotion.PromotionInsert;
import com.CloneShopee.DTO.Sale.Promotion.PromotionItemInsert;
import com.CloneShopee.DTO.Sale.product.ProductGenerate;
import com.CloneShopee.ExceptionGlobal.ConstraintException;
import com.CloneShopee.ResponeEntity.PageList;
import com.CloneShopee.models.Product;
import com.CloneShopee.models.Promotion;
import com.CloneShopee.models.PromotionFlashSale;
// import com.CloneShopee.models.PromotionCombo;
// import com.CloneShopee.models.PromotionComboOption;
import com.CloneShopee.models.PromotionItem;
import com.CloneShopee.models.PromotionProduct;
import com.CloneShopee.repository.ProductRepository;
// import com.CloneShopee.repository.PromotionComboOptionRepository;
// import com.CloneShopee.repository.PromotionComboRepository;
import com.CloneShopee.repository.PromotionItemRepository;
import com.CloneShopee.repository.PromotionProductRepository;
import com.CloneShopee.repository.PromotionRepository;

@Service
public class PromotionService {

    public static final String promotion_combo_type = "COMBO";
    public static final String promotion_product_type = "PRODUCT";

    @Autowired
    PromotionRepository promotionRepo;

    @Autowired
    PromotionItemRepository promotionItemRepo;

    @Autowired
    ProductRepository productRepo;

    @Autowired
    PromotionProductRepository promotionProductRepo;

    @Autowired
    private ShopBean shopBean;

    public PageList filterPromotion(String promotionName, Integer status, String productName, Integer page,
            String typeDiscount) {
        List<Integer> ids = null;
        if (!("FLASHSALE".equals(typeDiscount) || "PRODUCT".equals(typeDiscount))) {
            typeDiscount = null;
        }

        if (productName != null) {
            ids = promotionRepo.getPromotionIdsByProductName("%" + productName + "%", shopBean.getShop().getId());
        }
        Pageable pageble = PageRequest.of(page, 5);
        Page<PromotionInfo> p = promotionRepo.getAllPromotions(promotionName == null ? null : "%" + promotionName + "%",
                status, ids, pageble, typeDiscount);
        PageList promotion = new PageList(p.getTotalPages(), p.getTotalElements(), p.getContent());
        return promotion;
    }

    public List<Promotion> getAllPromotions() {
        return promotionRepo.findAll();
    }

    public void checkPromotionTime(Promotion promotion) {
        if (promotion.getEndDate().isBefore(promotion.getStartDate())) {
            throw new ConstraintException("startDate", "Start date must be before end date");
        }
        if (promotion.getStartDate().isAfter(LocalDateTime.now())) {
            throw new ConstraintException("startDate", "Start date must be after now");
        }
    }

    public void checkPromotionTime(Promotion promotion, LocalTime startTime, LocalTime endTime) {
        if (promotion.getEndDate().isBefore(promotion.getStartDate())
                || promotion.getStartDate().isAfter(LocalDateTime.now())) {
            throw new ConstraintException("startDate", "Start date must be before end date and after now");
        }
        if (startTime.isAfter(endTime)) {
            throw new ConstraintException("time", "Start time must be before end time");
        }
    }

    public void setOveralPromotion(Promotion promotion) {
        promotion.setId(null);
        promotion.setCreatedDate(new Date());
        promotion.setIsActive(1);
        promotion.setShop(shopBean.getShop());
    }

    public void checkProductIsApplingPromotion(Promotion promotion, Set<Integer> products) {
        products.forEach((v) -> {
            System.out.println("ID: " + v);
        });
        if (promotionRepo.countProductIsApplyPromotion(products, promotion.getStartDate(),
                promotion.getEndDate()) > 0) {
            throw new ConstraintException("products", "List product is Appling promotion another!!!");
        }
    }

    public List<PromotionItem> generatePromotionItem(Promotion p, Map<Integer, List<PromotionItemInsert>> productIds) {
        List<PromotionItem> promotionItems = new ArrayList<>();
        productIds.forEach((k, v) -> {
            v.forEach(vItem -> {
                if (vItem.getIsLimitUse() != 0 && vItem.getLimitUse() < 1) {
                    throw new ConstraintException("limitUse-Item",
                            "Số lược áp dụng sản phẩm phải lớn hơn 1 của sản phẩm id "
                                    + vItem.getProductVariantId());
                }
                promotionItems.add(new PromotionItem(p, vItem));
            });
        });
        return promotionItems;
    }

    public void savePromotion(Promotion promotion, List<PromotionItem> items) {
        try {
            promotionRepo.save(promotion);
            promotionItemRepo.saveAll(items);
        } catch (Exception e) {
            throw new ConstraintException("serverError", "There are errors with server, please try later");
        }
    }

    public void generateProductItem(Set<Integer> productIds, PromotionInsert<PromotionProduct> promotion) {
        Set<Integer> productGenerate = promotionRepo.getProductGenerate(productIds);
        AtomicInteger count = new AtomicInteger(0);
        promotion.getProductIds().forEach((k, v) -> {

            v.forEach(vv -> {
                if (!productGenerate.contains(vv.getProductVariantId())) {
                    throw new ConstraintException("productvariant", "Variant and product not match");
                }
                count.incrementAndGet();
            });
        });
        if (count.get() != productGenerate.size()) {
            throw new ConstraintException("productvariant", "Variant and product not match");
        }
    }

    public void generateProductItemF(Set<Integer> productIds, PromotionInsert<PromotionFlashSale> promotion) {
        Set<Integer> productGenerate = promotionRepo.getProductGenerate(productIds);

        AtomicInteger count = new AtomicInteger(0);
        promotion.getProductIds().forEach((k, v) -> {
            v.forEach(vv -> {
                count.incrementAndGet();
                if (!productGenerate.contains(vv.getProductVariantId())) {
                    throw new ConstraintException("productvariant", "Variant and product not match");
                }
            });
        });
        if (count.get() != productGenerate.size()) {
            System.out.println("-== " + count.get());
            throw new ConstraintException("productvariant", "Variant and product not match");
        }

    }

    public void savePromotionProduct(PromotionProduct promotion, Set<Integer> products) {
        List<PromotionItem> promotionItems = new ArrayList<>();
        promotionProductRepo.save(promotion);
        products.forEach(v -> {
            promotionItems.add(new PromotionItem(promotion, new Product(v)));
        });
        promotionItemRepo.saveAll(promotionItems);
    }

    public void checkProductOfShop(Set<Integer> products, Integer shopId) {
        if (productRepo.countProductInListAndOfShop(products, shopId) != products.size()) {
            throw new ConstraintException("products", "Product is not valid");
        }
    }

    public boolean deletePromotion(Integer shopId, Integer promotionId) {
        return promotionRepo.deletePromotion(promotionId, shopId) == 1;
    }

    public void updateItems(Promotion promotion, Set<Integer> promotionItems, Integer shopId) {
        checkProductOfShop(promotionItems, shopId);
        Set<Integer> promotionItemNow = promotionRepo.getSetProductIdInPromotionItem(promotion.getId());
        Set<Integer> promotionItemRemove = new HashSet<>(promotionItemNow);
        promotionItemRemove.removeAll(promotionItemNow);
        if (promotionItemRemove.size() > 0) {
            promotionItemRepo.deleteAllByIdInBatch(promotionItemRemove);
        }
        promotionItems.removeAll(promotionItemNow);
        if (promotionItems.size() > 0) {
            promotion.setPromotionItems(new ArrayList<>());
            promotionItems.forEach(v -> {
                promotion.getPromotionItems().add(new PromotionItem(promotion, new Product(v)));
            });
            promotionItemRepo.saveAll(promotion.getPromotionItems());
        }
    }

    public void updatePromotion(Promotion promotion) {
        promotionRepo.save(promotion);
    }

}
