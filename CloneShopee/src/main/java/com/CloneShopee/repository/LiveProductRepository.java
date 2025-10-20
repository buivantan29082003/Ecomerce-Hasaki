package com.CloneShopee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CloneShopee.models.LiveProduct;
import com.CloneShopee.models.LiveProductEmbededId;

public interface LiveProductRepository extends JpaRepository<LiveProduct, LiveProductEmbededId> {

}
