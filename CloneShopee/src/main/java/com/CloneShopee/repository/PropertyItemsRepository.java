package com.CloneShopee.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.CloneShopee.models.PropertyItem;

public interface PropertyItemsRepository extends JpaRepository<PropertyItem, Integer> {

	// SELECT==========================================================================

	// LẤY DANH SÁCH PROPPERTY CỦA PRODUCT
	@Query("SELECT p from PropertyItem p WHERE p.product.id=:productId")
	public List<PropertyItem> getPropertyItemInList(@Param("productId") Integer id);

	// LẤY SỐ LƯỢNG PROPERTY CỦA PRODUCT DỰA TRÊN PROPERTY_ID
	@Query("SELECT count(p.id) from PropertyItem p WHERE p.product.id=:productId and p.id in:propertyItemId")
	public Integer countPropertyByProductId(@Param("productId") Integer id,
			@Param("propertyItemId") List<Integer> propertuItemId);

	// UPDATE==========================================================================

	@Modifying
	@Query("DELETE PropertyItem p WHERE p.product.id=:productId")
	public void deleteAllPropertyByProductId(@Param("productId") Integer id);
}
