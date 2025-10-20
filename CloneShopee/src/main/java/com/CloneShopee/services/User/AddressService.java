package com.CloneShopee.services.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CloneShopee.Bean.ShopBean;
import com.CloneShopee.models.Address;
import com.CloneShopee.repository.AddressRepository;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepo;

    @Autowired
    ShopBean shopBean;

    public List<Address> getAllAddressOfUser() {
        return addressRepo.getAddressByAccountId(shopBean.getAccount().getId());
    }

    public Integer addAddress(Address address) {
        try {
            addressRepo.save(address);
            return 1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }
}
