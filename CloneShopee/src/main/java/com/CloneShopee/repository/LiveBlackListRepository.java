package com.CloneShopee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CloneShopee.models.LiveBlackList;
import com.CloneShopee.models.LiveEmbededId;

public interface LiveBlackListRepository extends JpaRepository<LiveBlackList, LiveEmbededId> {

}
