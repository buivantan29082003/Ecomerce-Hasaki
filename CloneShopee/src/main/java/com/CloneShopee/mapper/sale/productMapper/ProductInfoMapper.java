package com.CloneShopee.mapper.sale.productMapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.CloneShopee.DTO.Sale.product.ProductInfo;
import com.CloneShopee.models.Product;

@Mapper(componentModel = "spring")
public interface ProductInfoMapper {
	// @Mapping(source = "brand.brandName", target = "brandName")
	// List<ProductInfo> productToProductDTO(List<Product> products);
}
