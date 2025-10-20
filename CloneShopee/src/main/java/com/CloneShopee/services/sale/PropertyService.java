package com.CloneShopee.services.sale;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CloneShopee.models.Property;
import com.CloneShopee.repository.PropertyRepository;

@Service
public class PropertyService {

    @Autowired
    PropertyRepository propertyRepo;

    public List<Property> getAll() {
        return propertyRepo.findAll();
    }

    public List<Property> getByCategoryId(Integer id) {
        return propertyRepo.getPropertyByCateId(id);
    }
}
