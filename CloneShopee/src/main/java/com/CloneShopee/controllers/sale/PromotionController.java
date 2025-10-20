package com.CloneShopee.controllers.sale;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.CloneShopee.Bean.ShopBean;
import com.CloneShopee.DTO.Sale.Promotion.PromotionInsert;
import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.models.PromotionFlashSale;
import com.CloneShopee.models.PromotionItem;
import com.CloneShopee.models.PromotionProduct;
import com.CloneShopee.services.sale.ProductService;
import com.CloneShopee.services.sale.PromotionService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
public class PromotionController {

        @Autowired
        PromotionService promotionService;

        @Autowired
        private ProductService productService;

        @Autowired
        ShopBean shopbean;

        public ResponseEntity<Object> findAllPromotions() {

                return null;
        }

        @GetMapping("sale/promotion/findall")
        public ResponseEntity<Object> getAllPromotions(
                        @RequestParam(name = "promotionName", required = false) String promotionName,
                        @RequestParam(name = "productName", required = false) String productName,
                        @RequestParam(name = "status", required = false) Integer status,
                        @RequestParam(name = "typeDiscount", required = false) String typeDiscount,
                        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page) {

                return new ResponseEntity(new BaseRespone(
                                promotionService.filterPromotion(promotionName, status, productName, page,
                                                typeDiscount),
                                "success"),
                                HttpStatus.OK);
        }

        @Transactional
        @PutMapping("sale/promotion/delete")
        public ResponseEntity<Object> deletePromotion(@RequestParam("promotionId") Integer id) {
                return new ResponseEntity<>(promotionService.deletePromotion(shopbean.getShop().getId(), id)
                                ? "Delete successfully"
                                : "Khuyến mãi ko hợp lệ, chương trình có thể đã diễn ra hoặc ko phải của shop",
                                HttpStatus.OK);
        }

        @Transactional
        @PostMapping("sale/promotion/add/product")
        public ResponseEntity<Object> addPromotion(@RequestBody @Valid PromotionInsert<PromotionProduct> promotion) {
                Set<Integer> ids = promotion.getProductIds().keySet();
                promotionService.checkPromotionTime(promotion.getPromotion());
                promotionService.checkProductOfShop(ids, shopbean.getShop().getId());
                promotionService.checkProductIsApplingPromotion(promotion.getPromotion(), ids);
                promotionService.generateProductItem(ids, promotion);
                promotion.getPromotion().setPromotionType("PRODUCT");
                promotionService.setOveralPromotion(promotion.getPromotion());
                List<PromotionItem> items = promotionService.generatePromotionItem(promotion.getPromotion(),
                                promotion.getProductIds());
                promotionService.savePromotion(promotion.getPromotion(), items);
                return new ResponseEntity<>(new BaseRespone(null, "success"), HttpStatus.OK);
        }

        @Transactional
        @PostMapping("sale/promotion/add/falshsale")
        public ResponseEntity<Object> addPromotionFlashSale(
                        @RequestBody @Valid PromotionInsert<PromotionFlashSale> promotion) {
                System.err.println("-----------------------");
                Set<Integer> ids = promotion.getProductIds().keySet();
                promotionService.checkPromotionTime(promotion.getPromotion(), promotion.getPromotion().getStartTime(),
                                promotion.getPromotion().getEndTime());
                promotionService.checkProductOfShop(ids, shopbean.getShop().getId());
                promotionService.checkProductIsApplingPromotion(promotion.getPromotion(), ids);
                promotionService.generateProductItemF(ids, promotion);
                promotion.getPromotion().setPromotionType("FLASHSALE");
                promotionService.setOveralPromotion(promotion.getPromotion());
                List<PromotionItem> items = promotionService.generatePromotionItem(promotion.getPromotion(),
                                promotion.getProductIds());
                promotionService.savePromotion(promotion.getPromotion(), items);
                return new ResponseEntity<>(new BaseRespone(null, "success"), HttpStatus.OK);
        }

        @GetMapping("sale/promotion/checkonsale")
        public ResponseEntity<Object> getListProductApplyingPromotionByStartAndEndTime(
                        @RequestParam(name = "startDate", required = true) LocalDateTime startDate,
                        @RequestParam(name = "endDate", required = true) LocalDateTime endDate,
                        @RequestParam(name = "productIds", required = true) List<Integer> productId) {
                List<Integer> productIds = productService.checkListProductonSale(startDate, endDate, productId);
                return new ResponseEntity<>(new BaseRespone(productIds, "success"), HttpStatus.OK);
        }

}
