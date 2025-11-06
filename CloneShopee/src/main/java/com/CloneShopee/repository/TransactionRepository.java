package com.CloneShopee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.CloneShopee.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query("select p from Transaction p where p.order.id=:orderId")
    Transaction getTransactionByOrderId(@Param("orderId") Integer orderId);
}
