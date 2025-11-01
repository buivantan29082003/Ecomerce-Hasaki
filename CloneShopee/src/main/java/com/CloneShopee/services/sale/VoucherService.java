package com.CloneShopee.services.sale;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.CloneShopee.Bean.ShopBean;
import com.CloneShopee.DTO.Sale.Voucher.VoucherInfo;
import com.CloneShopee.DTO.Sale.Voucher.VoucherInsert;
import com.CloneShopee.DTO.Sale.Voucher.VoucherUpdate;
import com.CloneShopee.ExceptionGlobal.ConstraintException;
import com.CloneShopee.models.Order;
import com.CloneShopee.models.Product;
import com.CloneShopee.models.Shop;
import com.CloneShopee.models.VoucherShop;
import com.CloneShopee.models.VoucherShopItem;
import com.CloneShopee.repository.ProductRepository;
import com.CloneShopee.repository.VoucherRepository;
import com.CloneShopee.repository.VoucherShopItemRepository;
import com.CloneShopee.repository.SpecificationBuilder.VoucherSelerSpec;

import co.elastic.clients.util.DateTime;

@Service
public class VoucherService {

    private final String voucher_type_percent = "PERCENT";
    private final String voucher_type_price = "PRICE";

    @Autowired
    private ProductRepository productRepo;
    @Autowired
    VoucherRepository voucherRepo;
    @Autowired
    VoucherShopItemRepository voucherShopItemRepo;

    @Autowired
    ShopBean shopBean;

    public VoucherShop getVoucherByIdAndStarting(Integer voucherId) {
        return voucherRepo.getVoucherByIdAndStarting(voucherId);
    }

    public Page<VoucherInfo> getAllVoucher(String key, Integer status, Integer page, String productName) {
        List<Integer> l = null;
        if (productName != null && !productName.trim().equals("")) {
            l = voucherRepo.getVoucherIdsByProductName("%" + productName + "%");
        }
        Pageable p = PageRequest.of(page == null ? 0 : page - 1, 5, Sort.by("id"));
        return voucherRepo.findAllParentOnly(key == null ? "%%" : "%" + key + "%", status == 0 ? null : status, p,
                l, shopBean.getShop().getId());
    }

    public Object getOveralVoucher(Integer beforeDate) {
        return voucherRepo.getOveralVoucher(beforeDate);
    }

    public List<VoucherShop> getVoucherOfShopAndAccountReciveAndStarting(Integer accountId, Integer shopId) {
        return voucherRepo.getVoucherOfShopAndAccountReciveAndStarting(shopId, accountId);
    }

    public Boolean checkVoucherOfShop(Integer id, Integer shop) {
        System.out.println("---------------------------------");
        System.out.println(voucherRepo.getVoucherByIdAndShopId(id, shop));
        System.out.println("----------------------------------");
        if (voucherRepo.getVoucherByIdAndShopId(id, shop).orElse(-1) == -1) {
            return false;
        }
        return true;
    }

    public void saveVoucher(VoucherShop voucher, Shop shop, Set<Integer> productIds) {
        voucher.setShop(shop);
        voucher.setId(null);
        voucher.setTotalProduct(productIds.size());
        voucherRepo.save(voucher);
        List<VoucherShopItem> voucherItems = new ArrayList<>();
        for (Integer productId : productIds) {
            voucherItems.add(new VoucherShopItem(voucher, new Product(productId)));
        }

        voucherShopItemRepo.saveAll(voucherItems);
    }

    public void UpdateStatusVoucher(Integer voucherId, Integer isActive) {
        System.out.println(voucherId);
        voucherRepo.updateStatusVoucherByVoucherId(voucherId, isActive);
    }

    public void checkvalueDisCount(String voucherType, Double disCountValue) {
        switch (voucherType) {
            case voucher_type_percent:
                if (disCountValue > 100) {
                    throw new ConstraintException("discountValue", "Mus lessthan 100");
                }
                break;
            case voucher_type_price:
                break;
            default:
                throw new ConstraintException("voucherType", "Not find voucher type");
        }
    }

    public void checkTimeVoucher(LocalDateTime startDate, LocalDateTime endDate) {
        Objects.requireNonNull(startDate, "startDate");
        Objects.requireNonNull(endDate, "endDate");

        LocalDateTime now = LocalDateTime.now(); // hoặc ZonedDateTime/Instant nếu cần múi giờ

        // start phải > hiện tại
        if (!startDate.isAfter(now)) {
            throw new ConstraintException("startDate", "Start date must be greater than now");
        }
        // end phải > start
        if (!endDate.isAfter(startDate)) {
            throw new ConstraintException("endDate", "End date must be greater than start date");
        }
    }

    public void checkProductOfShop(Set<Integer> products, Integer shopId) {
        if (productRepo.countProductInListAndOfShop(products, shopId) != products.size()) {
            throw new ConstraintException("products", "Product is not valid");
        }
    }

    public void checkVoucherCode(String vocherCode) {
        Integer voucherId = voucherRepo.getVoucherIdByVoucherCodeAndShopId(shopBean.getShop().getId(), vocherCode);
        if (voucherId != null) {
            throw new ConstraintException("voucherCode", "Voucher code is not repeat");
        }
    }
    //
    // public void handleItemVoucher(Integer voucherId, Set<Integer> productIds,
    // Boolean isChange) {
    // if (isChange) {
    // voucherRepo.deleteVoucherItemInProductIds(productIds);
    // Set<Integer> productIdsOld = voucherRepo.getProductIdsOfVoucher(productIds,
    // voucherId);
    // productIds.removeAll(productIdsOld);
    // List<VoucherShopItem> voucherShopItems = new ArrayList<>();
    // VoucherShop voucher = new VoucherShop(voucherId);
    // for (Integer productId : productIds) {
    // voucherShopItems.add(new VoucherShopItem(voucher, new Product(productId)));
    // }
    // voucherShopItemRepo.saveAll(voucherShopItems);
    // }
    // }

    // public void updateInforVoucher(VoucherUpdate voucher) {
    // voucherRepo.updateVoucherShop(voucher.getId(), voucher.getVoucherName(),
    // voucher.getStartDate(),
    // voucher.getEndDate(), voucher.getVoucherType(), voucher.getDiscountValue(),
    // voucher.getLimitUsage(),
    // voucher.getLimitValue(), voucher.getIsActive());
    // }

}
