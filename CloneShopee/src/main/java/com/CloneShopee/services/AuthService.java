package com.CloneShopee.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CloneShopee.repository.AccountRepository;

@Service
public class AuthService {
    @Autowired
    AccountRepository accountRepo;

    public Integer getUserByEmail(String email, String password) {
        return accountRepo.getUserByEmailAndPasswrod(email, password).orElse(null);
    }
}
