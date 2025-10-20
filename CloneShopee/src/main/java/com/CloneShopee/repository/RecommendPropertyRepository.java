package com.CloneShopee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CloneShopee.models.Property;
import com.CloneShopee.models.PropertyValue;
import com.CloneShopee.models.RecommentProperty;

public interface RecommendPropertyRepository extends JpaRepository<RecommentProperty, Integer> {

    @Query("select p.property from RecommentProperty p where p.category.id=:cateId")
    List<Property> getAllPropertyByCateId(@Param("cateId") Integer cateId);

    @Query("select new com.CloneShopee.models.PropertyValue(p.id,p.propertyValue,p.property.id) from PropertyValue p where p.property.id in:propertyIds")
    List<PropertyValue> getALlPropertyValueByPropertyIds(@Param("propertyIds") List<Integer> propertyIds);

}
