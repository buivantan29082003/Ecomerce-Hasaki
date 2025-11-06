package com.CloneShopee.services.Common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CloneShopee.models.Account;
import com.CloneShopee.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepo;

    public Account getAccountById(Integer accountId) {
        return accountRepo.findById(accountId).orElse(null);
    }

}
