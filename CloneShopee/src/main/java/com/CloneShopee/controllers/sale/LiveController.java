package com.CloneShopee.controllers.sale;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CloneShopee.Bean.ShopBean;
import com.CloneShopee.DTO.Sale.Live.LiveInsert;
import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.models.LiveBlackList;
import com.CloneShopee.models.LiveProduct;
import com.CloneShopee.models.LiveSession;
import com.CloneShopee.services.sale.LiveService;
import com.CloneShopee.services.sale.ProductService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
public class LiveController {

    @Autowired
    LiveService liveService;

    @Autowired
    ProductService productService;

    @Autowired
    ShopBean shopBean;

    @Transactional
    @PostMapping("sale/live/product/add")
    public ResponseEntity<Object> addProductForLive(@RequestBody Set<Integer> productIds,
            @RequestParam(name = "liveId", defaultValue = "-1") Integer liveId) {
        if (liveService.checkLiveOfShop(liveId, shopBean.getShop().getId())) {
            if (liveService.checkProductOfShop(productIds, shopBean.getShop().getId())) {
                Set<Integer> productOfLive = liveService.getProductIdsOfLive(liveId);
                productIds.removeAll(productOfLive);
                List<LiveProduct> liveProducts = new ArrayList<>();
                productIds.forEach(v -> liveProducts.add(new LiveProduct(v, liveId)));
                return new ResponseEntity<>(new BaseRespone(null, "Thêm thành công sản phẩm vào live"),
                        HttpStatus.OK);
            }
            return new ResponseEntity<>(new BaseRespone(null, "Danh sách sản phẩm không hợp lệ"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new BaseRespone(null, "Phiên live cập nhật không thuộc shop"),
                HttpStatus.BAD_REQUEST);
    }

    // @PostMapping("sale/live/react/product/plusCart")
    // public ResponseEntity<Object> reactAddToCart(Integer productU){

    // }

    @Transactional
    @PostMapping("/sale/live/blacklist/add")
    public ResponseEntity<Object> addBlackList(@RequestParam(name = "accountId", defaultValue = "-1") Integer accountId,
            @RequestParam(name = "liveId", defaultValue = "-1") Integer liveId) {
        if (liveService.checkLiveOfShop(liveId, shopBean.getShop().getId())
                && liveService.checkAccountBlackListSingle(accountId)) {
            liveService.addBlackList(new LiveBlackList(liveId, accountId));
            return new ResponseEntity<>(new BaseRespone(null, "success"), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    @PostMapping("/sale/live/blacklist/remove")
    public ResponseEntity<Object> removeBlackList(
            @RequestParam(name = "accountId", defaultValue = "-1") Integer accountId,
            @RequestParam(name = "liveId", defaultValue = "-1") Integer liveId) {
        if (liveService.checkLiveOfShop(liveId, shopBean.getShop().getId())) {
            liveService.removeBlackList(liveId, accountId);
            return new ResponseEntity<>(new BaseRespone(null, "success"), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Transactional
    @PostMapping("sale/live/add")
    public ResponseEntity<Object> addLive(@RequestBody @Valid LiveInsert live) {
        if (live.getBlackListId() != null && live.getBlackListId().size() > 0) {
            if (!liveService.checkAccountBlackList(live.getBlackListId())) {
                return new ResponseEntity<Object>(
                        new BaseRespone(null, "Danh sách giới hạn của phiên live không hợp lệ"),
                        HttpStatus.BAD_REQUEST);
            }
        }
        live.getLive().setId(null);
        live.getLive().setShop(shopBean.getShop());
        liveService.saveLive(live.getLive(), live.getBlackListId());
        return new ResponseEntity<>(new BaseRespone(live.getLive().getId(), "success"), HttpStatus.OK);
    }

    @GetMapping("sale/live/all")
    public ResponseEntity<Object> getALl() {
        return new ResponseEntity<>(new BaseRespone(liveService.getAll(), "success"), HttpStatus.OK);

    }

}