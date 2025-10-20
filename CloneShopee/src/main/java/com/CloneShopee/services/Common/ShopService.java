package com.CloneShopee.services.Common;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CloneShopee.DTO.User.Shop;
import com.CloneShopee.repository.ShopRepository;

@Service
public class ShopService {

    @Autowired
    ShopRepository shopRepo;

    public List<Shop> getShopDTOUserInShopIds(Set<Integer> shopIds) {
        return shopRepo.getShopInfoDTOUser(shopIds);
    }
}
