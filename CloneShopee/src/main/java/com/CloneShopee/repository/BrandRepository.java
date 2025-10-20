package com.CloneShopee.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.CloneShopee.models.Brand;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

	@Query("SELECT p.id from Brand p where p.id = :id")
	public Optional<Integer> getBrandById(@Param("id") Integer id);
}
