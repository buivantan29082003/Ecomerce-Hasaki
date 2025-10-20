package com.CloneShopee.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CloneShopee.models.LiveSession;

public interface LiveSessionRepository extends JpaRepository<LiveSession, Integer> {

    @Modifying
    @Query("update LiveProduct p set p.countReact=p.countReact+1 where p.id.productId=:productId and p.id.liveId=:liveId")
    public Integer plusReact(@Param("liveId") Integer liveId, @Param("productId") Integer productId);

    @Modifying
    @Query("update LiveProduct p set p.countAddToCart=p.countAddToCart+1 where p.id.productId=:productId and p.id.liveId=:liveId")
    public Integer plusAddToCart(@Param("liveId") Integer liveId, @Param("productId") Integer productId);

    @Query("select p.id from LiveSession p where p.id=:liveId and p.startDate is NULL")
    public Optional<Integer> checkLiveIsAlive(@Param("liveId") Integer liveId);

    @Query("SELECT count(p.id) FROM Account p where p.id in:accountIds")
    Integer countAccountInList(@Param("accountIds") List<Integer> accountIds);

    @Query("SELECT p.id FROM Account p where p.id =:accountId")
    Optional<Integer> countAccountById(@Param("accountId") Integer accountIds);

    @Query("SELECT p.id from LiveSession p where p.id=:liveId and p.shop.id=:shopId")
    Optional<Integer> checkLiveOfShop(@Param("liveId") Integer liveId, @Param("shopId") Integer shopId);

    @Modifying
    @Query("DELETE  LiveBlackList p where p.id.liveId=:liveId and p.id.accountId=:accountId")
    void deleteBlackList(@Param("liveId") Integer liveId, @Param("accountId") Integer accountId);

    @Query("select p.product.id from LiveProduct p where p.live.id=:liveId")
    public Set<Integer> getProductIdsOfLive(@Param("liveId") Integer liveId);

}
