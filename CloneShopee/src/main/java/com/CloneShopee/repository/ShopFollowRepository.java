package com.CloneShopee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.CloneShopee.models.ShopFollow;

@Repository
public interface ShopFollowRepository extends JpaRepository<ShopFollow, Integer> {

    @Query("select p.id.accountId from ShopFollow p where p.id.accountId=:accountId and p.id.shopId=:shopId")
    Integer checkFollowedShop(@Param("shopId") Integer shopId, @Param("accountId") Integer accountId);

    @Modifying
    @Query("DELETE FROM ShopFollow p where p.id.shopId=:shopId and p.id.accountId=:accountId")
    Integer unFollow(@Param("shopId") Integer shopId, @Param("accountId") Integer accountId);

    @Query("SELECT DATEDIFF(CURRENT_DATE, sf.dateFollowed) FROM ShopFollow sf WHERE sf.id.shopId = :shopId AND sf.id.accountId = :accountId")
    Integer getFollowDuration(@Param("shopId") Integer shopId, @Param("accountId") Integer accountId);

}
