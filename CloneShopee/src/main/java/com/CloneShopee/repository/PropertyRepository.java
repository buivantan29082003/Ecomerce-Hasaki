package com.CloneShopee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CloneShopee.models.Property;

public interface PropertyRepository extends JpaRepository<Property, Integer> {
	@Query("SELECT p from Property p where p.id in :ids")
	public List<Property> getPropertyInList(@Param("ids") List<Integer> ids);

	@Query("SELECT p.property from RecommentProperty p where p.category.id =:cateId")
	public List<Property> getPropertyByCateId(@Param("cateId") Integer id);
}
