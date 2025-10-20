package com.CloneShopee.services.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CloneShopee.models.ShopFollow;
import com.CloneShopee.repository.ShopFollowRepository;

@Service
public class FollowService {

    @Autowired
    private ShopFollowRepository shopFollowRepo;

    public Integer countDayFollowed(Integer shopId, Integer accountId) {
        return shopFollowRepo.getFollowDuration(shopId, accountId);
    }

    public void followShop(ShopFollow shopFollow) {
        shopFollowRepo.save(shopFollow);
    }

    public Integer unFollow(Integer shopId, Integer accountId) {
        return shopFollowRepo.unFollow(shopId, accountId);
    }

    public Integer checkFollowedShop(Integer shopId, Integer accountId) {
        return shopFollowRepo.checkFollowedShop(shopId, accountId);
    }

}
