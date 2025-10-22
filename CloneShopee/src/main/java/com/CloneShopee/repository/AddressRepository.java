package com.CloneShopee.repository;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.CloneShopee.models.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query("SELECT p FROM Address p WHERE p.account is not null and  p.account.id = :accountId")
    List<Address> getAddressByAccountId(@Param("accountId") Integer accountId);

    @Query("select p from Address p where p.id=:id and p.account.id=:accountId")
    Address getAddressByIdAndAccountId(@Param("id") Integer addressId, @Param("accountId") Integer accountId);
}
