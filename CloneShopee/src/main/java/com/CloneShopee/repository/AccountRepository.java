package com.CloneShopee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.CloneShopee.models.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query(value = "SELECT p.id FROM Account p WHERE p.email =:email AND p.password = :password", nativeQuery = true)
    Optional<Integer> getUserByEmailAndPasswrod(@Param("email") String email, @Param("password") String password);

}
