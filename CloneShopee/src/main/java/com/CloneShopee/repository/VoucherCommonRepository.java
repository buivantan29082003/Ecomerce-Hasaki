package com.CloneShopee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CloneShopee.models.VoucherCommon;

@Repository
public interface VoucherCommonRepository extends JpaRepository<VoucherCommon, Integer> {

}
