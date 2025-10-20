package com.CloneShopee.services.sale;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CloneShopee.repository.LiveSessionRepository;

@Service
public class LiveSessionService {

    @Autowired
    LiveSessionRepository liveSessionRepo;

    public Boolean checkAccountBlackList(List<Integer> accountIds) {
        if (accountIds.size() == liveSessionRepo.countAccountInList(accountIds)) {
            return true;
        }
        return false;
    }

}
