package com.CloneShopee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CloneShopee.models.Category;

public interface Categoryrepository extends JpaRepository<Category, Integer> {
	@Query("SELECT p.id from Category p where p.id = :id")
	public Optional<Integer> getBrandById(@Param("id") Integer id);

	@Query("SELECT c FROM Category c LEFT JOIN FETCH c.children WHERE c.parent IS NULL")
	List<Category> findAllParentCategories();

	@Query(value = """
			WITH RECURSIVE subcategorie AS (
			    SELECT id, parentId FROM categorie WHERE id IN (:ids)
			    UNION ALL
			    SELECT c.id, c.parentId
			    FROM categorie c
			    INNER JOIN subcategorie s ON c.parentId = s.id
			)
			SELECT id FROM subcategorie WHERE id NOT IN (:ids)
			""", nativeQuery = true)
	List<Integer> findAllChildrenRecursive(@Param("ids") List<Integer> ids);

	@Query("select p from Category p where p.id=:id")
	Category getCateById(@Param("id") Integer id);

	@Query("SELECT new com.CloneShopee.models.Category(c.id,c.categoryName,c.image)  FROM Category c ")
	List<Category> findAllParentCategoriesWithEmptyChild();
}
