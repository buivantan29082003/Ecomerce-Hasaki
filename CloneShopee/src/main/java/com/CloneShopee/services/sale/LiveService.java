package com.CloneShopee.services.sale;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.shaded.freemarker.core.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CloneShopee.models.LiveBlackList;
import com.CloneShopee.models.LiveComment;
import com.CloneShopee.models.LiveProduct;
import com.CloneShopee.models.LiveSession;
import com.CloneShopee.repository.LiveBlackListRepository;
import com.CloneShopee.repository.LiveCommentRepository;
import com.CloneShopee.repository.LiveProductRepository;
import com.CloneShopee.repository.LiveSessionRepository;
import com.CloneShopee.repository.ProductRepository;

@Service
public class LiveService {
    @Autowired
    LiveSessionRepository liveSessionRepo;

    @Autowired
    ProductRepository productRepo;

    @Autowired
    LiveBlackListRepository liveBlackListRepo;

    @Autowired
    LiveProductRepository liveProductRepo;

    @Autowired
    LiveCommentRepository liveCommentRepo;

    // public LiveSession checkAliveAndGetLive(Integer liveId){

    // }

    public List<LiveComment> getAllComments(Integer liveId) {
        return liveCommentRepo.findByLiveId(liveId);
    }

    public Boolean plusCounReact(Integer liveId, Integer productId) {
        return liveSessionRepo.plusReact(liveId, productId) > 0;
    }

    public Boolean plusCounCart(Integer liveId, Integer productId) {
        return liveSessionRepo.plusAddToCart(liveId, productId) > 0;
    }

    public Integer checkAlive(Integer liveId) {
        return liveSessionRepo.checkLiveIsAlive(liveId).orElse(null);
    }

    public LiveSession getLiveById(Integer liveId) {
        return liveSessionRepo.findById(liveId).orElse(null);
    }

    public void addProductInLive(List<LiveProduct> liveProducts) {
        liveProductRepo.saveAll(liveProducts);
    }

    public Boolean checkProductOfShop(Set<Integer> productIds, Integer shopId) {
        return productRepo.countProductInListAndOfShop(productIds, shopId) == productIds.size();
    }

    public Set<Integer> getProductIdsOfLive(Integer liveId) {
        return liveSessionRepo.getProductIdsOfLive(liveId);
    }

    public void addBlackList(LiveBlackList liveBlackList) {
        liveBlackListRepo.save(liveBlackList);
    }

    public Boolean checkAccountBlackList(List<Integer> accountIds) {
        if (accountIds.size() == liveSessionRepo.countAccountInList(accountIds)) {
            return true;
        }
        return false;
    }

    public Boolean checkAccountBlackListSingle(Integer accountId) {
        if (liveSessionRepo.countAccountById(accountId).orElse(null) != null) {
            return true;
        }
        return false;
    }

    public void removeBlackList(Integer liveId, Integer accountId) {
        liveSessionRepo.deleteBlackList(liveId, accountId);
    }

    public void saveLive(LiveSession live, List<Integer> blackListId) {
        liveSessionRepo.save(live);
        if (blackListId != null && blackListId.size() > 0) {
            List<LiveBlackList> blackList = new ArrayList<>();
            blackListId.forEach(v -> blackList.add(new LiveBlackList(live.getId(), v)));
            liveBlackListRepo.saveAll(blackList);
        }
    }

    public List<LiveSession> getAll() {
        return liveSessionRepo.findAll();
    }

    public Boolean checkLiveOfShop(Integer liveId, Integer shopId) {
        return liveSessionRepo.checkLiveOfShop(liveId, shopId).orElse(null) != null;
    }

}
