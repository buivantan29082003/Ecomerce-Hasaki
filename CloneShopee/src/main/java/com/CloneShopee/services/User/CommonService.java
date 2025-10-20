package com.CloneShopee.services.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CloneShopee.models.Brand;
import com.CloneShopee.models.Property;
import com.CloneShopee.models.PropertyValue;
import com.CloneShopee.repository.RecommendBrandRepository;
import com.CloneShopee.repository.RecommendPropertyRepository;

@Service
public class CommonService {
    @Autowired
    RecommendBrandRepository recommendBrandRepo;

    @Autowired
    RecommendPropertyRepository recommendPropertyRepo;

    // brand
    public List<Brand> getRecommentBrandByCategoryId(List<Integer> cateIds) {
        if (cateIds.size() > 0) {
            return recommendBrandRepo.getBrandByCategoryIds(cateIds);
        }
        return new ArrayList<>();
    }

    public List<Property> getAllPropertyRecommendByCateId(Integer cateId) {
        List<Property> properties = recommendPropertyRepo.getAllPropertyByCateId(cateId);
        List<PropertyValue> propertyValues = recommendPropertyRepo
                .getALlPropertyValueByPropertyIds(properties.stream().map(v -> v.getId()).toList());
        Map<Integer, List<PropertyValue>> grouped = propertyValues.stream()
                .collect(Collectors.groupingBy(pv -> pv.getProperty().getId()));
        properties.forEach(v -> {
            v.setPropertyValues(grouped.get(v.getId()));
        });
        return properties;
    }
}
