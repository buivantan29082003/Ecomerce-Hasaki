package com.CloneShopee.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CloneShopee.models.Shop;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
    // Integer checkShopIsLive(@Param("shopId") shopId);

    @Query("select new com.CloneShopee.DTO.User.Shop(p.id,p.shopName,p.districtId,p.wardCode) from Shop p where p.id in:shopIds")
    List<com.CloneShopee.DTO.User.Shop> getShopInfoDTOUser(@Param("shopIds") Set<Integer> shopIds);
}
