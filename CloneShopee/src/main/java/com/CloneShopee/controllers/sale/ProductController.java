package com.CloneShopee.controllers.sale;

import org.springframework.web.bind.annotation.RestController;

import com.CloneShopee.Bean.ShopBean;
import com.CloneShopee.DTO.Sale.product.ProductInfo;
import com.CloneShopee.ExceptionGlobal.ConstraintException;
import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.ResponeEntity.PageList;
import com.CloneShopee.models.Product;
import com.CloneShopee.services.sale.ProductService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin("*")
public class ProductController {
	@Autowired
	ProductService productService;

	@Autowired
	ShopBean shopBean;

	// API lấy danh sách lọc sản phẩm
	@GetMapping("/sale/product/findall")
	public ResponseEntity<Object> getProduct(@RequestParam(name = "key", required = false) String productName,
			@RequestParam(name = "status", required = false) Integer status,
			@RequestParam(name = "minPrice", required = false) Double minPrice,
			@RequestParam(name = "maxPrice", required = false) Double maxPrice,
			@RequestParam(name = "categories", required = false) List<Integer> categories,
			@RequestParam(name = "isApplyingPromotion", required = false) Boolean isApplyingPromotion,
			@RequestParam(name = "isLive", required = false) Boolean isLive,
			@RequestParam(name = "page", required = false) Integer page) {
		page = page == null || page - 1 < 0 ? 0 : page - 1;
		Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());
		Page<Product> product = productService.filterProducts(productName, categories, status, null, minPrice,
				maxPrice, isApplyingPromotion, isLive, pageable);
		List<ProductInfo> products = new ArrayList<>();
		Map<Integer, Integer> sumaryVariants = productService.sumVariantQuantity(product.getContent().stream()
				.map(Product::getId)
				.collect(Collectors.toList()));
		product.getContent().forEach(v -> {
			ProductInfo p = new ProductInfo(v);
			p.setQuantity(sumaryVariants.get(p.getId()));
			products.add(p);
		});
		return new ResponseEntity<Object>(
				new BaseRespone(new PageList(product.getTotalPages(), product.getTotalElements(), products), "success"),
				HttpStatus.OK);
	}

	@PostMapping("/sale/product/getallvariantbyproduct")
	public ResponseEntity<Object> getVariantListByProductIds(@RequestBody List<Integer> productIds) {
		if (productIds != null && productIds.size() > 0) {
			return new ResponseEntity<>(
					new BaseRespone(productService.getProductVariantsByProductIds(productIds), "success"),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(new BaseRespone(null, "ko có procuctIds"), HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/sale/product/findallwithvariant")
	public ResponseEntity<Object> getProductWithVariant(
			@RequestParam(name = "key", required = false) String productName,
			@RequestParam(name = "status", required = false) Integer status,
			@RequestParam(name = "minPrice", required = false) Double minPrice,
			@RequestParam(name = "maxPrice", required = false) Double maxPrice,
			@RequestParam(name = "categories", required = false) List<Integer> categories,
			@RequestParam(name = "startDate", required = false) LocalDateTime startDate,
			@RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
			@RequestParam(name = "page", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Integer page) {
		page = page == null || page - 1 < 0 ? 0 : page - 1;
		Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());
		Page<Product> product = productService.filterProductNotIn(productName, categories, status, null, minPrice,
				maxPrice, startDate, endDate, pageable);
		List<ProductInfo> products = new ArrayList<>();
		product.getContent().forEach(v -> {
			ProductInfo p = new ProductInfo(v);
			products.add(p);
		});
		return new ResponseEntity<Object>(
				new BaseRespone(new PageList(product.getTotalPages(), product.getTotalElements(), products), "success"),
				HttpStatus.OK);
	}

	@Transactional
	@PostMapping("/sale/product/update/status")
	public ResponseEntity<Object> updateStatusProduct(@RequestParam("status") String status,
			@RequestParam("productId") Integer productId) {
		productService.updateStatusProduct(status, productId, shopBean.getShop().getId());
		return new ResponseEntity<Object>(new BaseRespone(null, "success"), HttpStatus.OK);
	}

	@Transactional
	@PutMapping("/sale/product/update")
	public ResponseEntity<Object> updateProduct(@RequestBody @Valid Product product) {
		productService.updateProperties(product, product.getProperties(), product.getCategory().getId());
		productService.updateProduct(product);
		// productService.updateVariantTier(product.getProductVariants(), product,
		// true); CẦN CHỈNH SỬA
		return new ResponseEntity<Object>("Update product  success", HttpStatus.OK);
	}

	@Transactional
	@PostMapping("/sale/product/add")
	public ResponseEntity<Object> insertProduct(@RequestBody @Valid Product product) throws ConstraintException {
		productService.checkVariant(product.getProductVariants(), product);
		productService.checkProperties(product.getProperties(), product);
		productService.checkBrand(product.getBrand().getId());
		product.setId(null);
		productService.checkCategory(product.getCategory().getId());
		product.setShop(shopBean.getShop());
		productService.saveProduct(product);
		return new ResponseEntity<Object>(new BaseRespone(product.getId(), "Thêm thành công sản phẩm"),
				HttpStatus.OK);
	}

}
