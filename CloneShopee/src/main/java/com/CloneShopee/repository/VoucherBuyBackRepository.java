package com.CloneShopee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CloneShopee.models.VoucherBuyBack;

@Repository
public interface VoucherBuyBackRepository extends JpaRepository<VoucherBuyBack, Integer> {

}
