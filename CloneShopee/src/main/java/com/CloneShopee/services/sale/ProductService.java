package com.CloneShopee.services.sale;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.hibernate.boot.beanvalidation.IntegrationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.CloneShopee.Bean.ShopBean;
import com.CloneShopee.DTO.User.PromotionInProductSearch;
import com.CloneShopee.ExceptionGlobal.ConstraintException;
import com.CloneShopee.models.Product;
import com.CloneShopee.models.ProductVariant;
import com.CloneShopee.models.Promotion;
import com.CloneShopee.models.Property;
import com.CloneShopee.models.PropertyItem;
import com.CloneShopee.models.VariantTier;
import com.CloneShopee.models.VoucherShop;
import com.CloneShopee.repository.BrandRepository;
import com.CloneShopee.repository.Categoryrepository;
import com.CloneShopee.repository.ProductImagesReopository;
import com.CloneShopee.repository.ProductRepository;
import com.CloneShopee.repository.ProductVariantRepository;
import com.CloneShopee.repository.PromotionRepository;
import com.CloneShopee.repository.PropertyItemsRepository;
import com.CloneShopee.repository.PropertyRepository;
import com.CloneShopee.repository.VariantTierRepository;
import com.CloneShopee.repository.VoucherRepository;
import com.CloneShopee.repository.SpecificationBuilder.ProductSpecification;

@Service
public class ProductService {
	@Autowired
	ProductRepository productRepo;
	@Autowired
	PropertyRepository propertyRepo;
	@Autowired
	BrandRepository brandRepo;
	@Autowired
	Categoryrepository categoryRepo;
	@Autowired
	VariantTierRepository variantTierRepo;
	@Autowired
	ProductVariantRepository productVariantRepo;
	@Autowired
	PropertyItemsRepository propertyItemRepo;
	@Autowired
	ProductImagesReopository productImagesRepo;
	@Autowired
	PromotionRepository promotionRepo;

	@Autowired
	VoucherRepository voucherRepo;

	@Autowired
	private ShopBean shopbean;

	private final Map<String, Integer> statusProduct = new HashMap<>(Map.of(
			"ACTIVE", 1,
			"HIDDEN", 0,
			"DELETE", 2));

	public List<VoucherShop> getALlVoucherOfShop(Integer shopId) {
		return voucherRepo.getAllVoucherByShopId(shopId);
	}

	public Product getProductById(Integer id) {
		return productRepo.findById(id).orElse(null);
	}

	public List<Integer> checkListProductonSale(LocalDateTime startDate, LocalDateTime endDate, List<Integer> ids) {
		return productRepo.getProductNotApplyingProductInList(shopbean.getShop().getId(), startDate, endDate, ids);
	}

	public Promotion getPromotionIsActiveByIdProduct(Integer productId) {
		return promotionRepo.getPromotionIsActiveAndByProductId(productId);
	}

	public List<PromotionInProductSearch> getPromotionInfoOfProduct(List<Product> products) {
		return promotionRepo.getPromotionInfoInListProduct(products);
	}

	public Map<Integer, Integer> sumVariantQuantity(List<Integer> productIds) {
		List<Integer[]> l = productRepo.getSumaryQuantityVariantInProducts(productIds);
		Map<Integer, Integer> resultQuantitys = new HashMap<>();
		l.forEach(v -> {
			resultQuantitys.put(v[0], v[1]);
		});
		return resultQuantitys;
	}

	public Page<Product> filterProducts(String name, List<Integer> categoryIds, Integer status,
			List<Integer> brandIds, Double minPrice, Double maxPrice, Boolean isApplyingPromotion, Boolean isLive,
			Pageable page) {
		List<Integer> productIsLive = null;
		if (isLive == true) {
			productIsLive = productRepo.getProductOfShopIsLive(shopbean.getShop().getId());
		}

		Specification<Product> spec = ProductSpecification.filterProductsWithJoinfetch(name, categoryIds, status,
				brandIds, minPrice, maxPrice,
				isApplyingPromotion != null ? productRepo.getAllIdsProductApplyingPromotion(shopbean.getShop().getId())
						: null,
				shopbean.getShop().getId(), productIsLive);

		return productRepo.findAll(spec, page);
	}

	public Page<Product> filterProductNotIn(String name, List<Integer> categoryIds, Integer status,
			List<Integer> brandIds, Double minPrice, Double maxPrice, LocalDateTime startDate, LocalDateTime endDate,
			Pageable page) {
		List<Integer> productIds = productRepo.getProductNotApplyingProduct(shopbean.getShop().getId(), startDate,
				endDate);
		Specification<Product> spec = ProductSpecification.filterProductsWithJoinfetchNotIn(name, categoryIds, status,
				brandIds, minPrice, maxPrice, shopbean.getShop().getId(), productIds);
		return productRepo.findAll(spec, page);
	}

	public List<Integer> getAllIdProductApplyingPromotion(Integer shopId) {
		return productRepo.getAllIdsProductApplyingPromotion(shopId);
	}

	public Map<Integer, List<ProductVariant>> getProductVariantsByProductIds(List<Integer> productIds) {
		List<ProductVariant> l = productRepo.getProductVariantsByProductIds(productIds);
		Map<Integer, List<ProductVariant>> groupedByProductId = l.stream()
				.collect(Collectors.groupingBy(v -> v.getProduct().getId()));
		return groupedByProductId;
	}

	public List<Product> findAllProducts() {
		return productRepo.findAll();
	}

	public Product findByIdFullProperties(Integer productId) {
		Product product = productRepo.findByIdFullProperties(productId).orElse(null);
		if (product != null) {
			product.getProductImage();
			product.getProductVariants();
			// product.setPromotionItems(null);
		}
		return product;
	}

	public List<PromotionInProductSearch> getPromotionOfProductId(Integer productId) {
		return promotionRepo.getPromotionInfoByProductid(productId);
	}

	public void updateStatusProduct(String status, Integer productId, Integer shopId) {
		if (statusProduct.get(status) != null) {
			Integer product = productRepo.getIdProductByProductIdAndShopId(productId, shopId);
			if (product != null) {
				productRepo.updateStatusByProductId(productId, statusProduct.get(status));
			} else {
				throw new ConstraintException("product", "Product not of shop !!!");
			}
		}
		throw new ConstraintException("status", "Status change is not valid !!!");
	}

	public void updateProperties(Product product, List<PropertyItem> propertyItems, Integer categoryOld) {
		if (propertyItems == null || propertyItems.isEmpty()) {
			return;
		}
		if (categoryOld.equals(product.getCategory().getId())) {
			checkProperties(propertyItems, product);
			List<Integer> ids = propertyItems.stream()
					.map(PropertyItem::getId)
					.filter(id -> id != null)
					.collect(Collectors.toList());
			Integer countPropertyCheck = propertyItemRepo.countPropertyByProductId(product.getId(), ids);
			if (countPropertyCheck != ids.size()) {
				throw new RuntimeException("Property update Item not match");
			}
		} else {
			deleteAllPropertyByProductId(product.getId());
			checkProperties(propertyItems, product);
		}
		propertyItems.forEach(v -> {
			v.setProduct(product);
		});
		propertyItemRepo.saveAll(propertyItems);
	}

	public void updateProduct(Product product) {
		productRepo.save(product);
	}

	public void deleteAllPropertyByProductId(Integer productId) {
		propertyItemRepo.deleteAllPropertyByProductId(productId);
	}

	public void checkProperties(List<PropertyItem> propertyItems, Product p) {
		if (propertyItems == null) {
			throw new ConstraintException("properties", "Trường properties này là bắt buộc");
		}
		List<Integer> proRecomment = productRepo.getPropertiesRecomend(p.getCategory().getId());

		List<Integer> propertyIds = propertyItems.stream()
				.map(item -> item.getProperty().getId())
				.collect(Collectors.toList());
		if (propertyIds.containsAll(proRecomment) != true) {
			throw new ConstraintException("properties", "Chưa đủ số thuộc tính yêu cầu");
		}
		List<Property> properties = propertyRepo.getPropertyInList(propertyIds);
		Map<Integer, String> propertyMap = properties.stream()
				.collect(Collectors.toMap(Property::getId, Property::getHashValue));
		propertyItems.forEach(v -> {
			String value = propertyMap.get(v.getProperty().getId());
			if (value != null && value.contains("-" + v.getPropertyValue() != null ? v.getPropertyValue().getId() + "-"
					: "ow[uow[uow[ươ]]]" + "-")) {
				v.setProduct(p);
			} else {
				throw new ConstraintException("properties", "Property and value not match");
			}
		});
		System.out.println("Tới đây rồi á");
	}

	public void checkBrand(Integer id) throws ConstraintException {
		if (brandRepo.findById(id).orElse(null) == null) {
			throw new ConstraintException("brand", "Thương hiệu bạn chọn không hợp lý ");
		}
	}

	public void saveProduct(Product product) {
		productRepo.save(product);
		propertyItemRepo.saveAll(product.getProperties());
		productVariantRepo.saveAll(product.getProductVariants());
		product.getProductImages().forEach(v -> {
			v.setProduct(product);
			v.setId(null);
		});
		productImagesRepo.saveAll(product.getProductImages());
	}

	public static DoubleSummaryStatistics getPriceStats(List<ProductVariant> productVariants) {
		return productVariants.stream()
				.mapToDouble(ProductVariant::getPrice) // Chuyển sang double
				.summaryStatistics(); // Lấy min, max, average, count
	}

	public void sumaryPrice(List<ProductVariant> v, Product p) {
		DoubleSummaryStatistics c = getPriceStats(v);
		if (c.getMax() >= c.getMin()) {
			p.setMinPrice(c.getMin());
			p.setMaxPrice(c.getMax());
		}
	}

	public void checkCategory(Integer id) throws ConstraintException {
		if (categoryRepo.findById(id).orElse(null) == null) {
			throw new ConstraintException("category", "Category your choose is not valid");
		}
	}

	public void checkVariant(List<ProductVariant> productVariant, Product product)
			throws ConstraintException {
		boolean exists = productVariant.stream()
				.map(ProductVariant::getVariantName)
				.collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()))
				.values()
				.stream()
				.anyMatch(count -> count > 1);

		if (exists) {
			throw new ConstraintException("productVariant", "Tên các biến thể không được trùng nhau.");
		}
		productVariant.forEach(v -> {
			v.setProduct(product);
			v.setId(null);
		});
		double min = productVariant.stream()
				.mapToDouble(ProductVariant::getPrice)
				.min()
				.orElse(0);
		double max = productVariant.stream()
				.mapToDouble(ProductVariant::getPrice)
				.max()
				.orElse(0);
		product.setMinPrice(min);
		product.setMaxPrice(max);

	}

}
