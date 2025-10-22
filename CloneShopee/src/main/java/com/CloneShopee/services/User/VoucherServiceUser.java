package com.CloneShopee.services.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CloneShopee.Bean.ShopBean;
import com.CloneShopee.DTO.User.VoucherApllyInfo;
import com.CloneShopee.models.VoucherShopAccount;
import com.CloneShopee.repository.VoucherRepository;
import com.CloneShopee.repository.VoucherShopAccountRepository;

@Service
public class VoucherServiceUser {

    @Autowired
    ShopBean shopBean;

    @Autowired
    VoucherShopAccountRepository voucherShopAccountRepo;

    @Autowired
    VoucherRepository voucherRepo;

    public List<VoucherApllyInfo> getAllVoucherByProduct(Map<Integer, Double> sumaryProduct) {
        List<VoucherApllyInfo> vouchers = voucherRepo.getAllVoucherByProductIds();
        Map<Integer, Integer> voucherAccountMap = getVoucherGain(vouchers.stream().map(v -> v.getVoucherId()).toList());
        vouchers.forEach(v -> {
            Integer quantityUsed = voucherAccountMap.get(v.getVoucherId());
            if (quantityUsed == null) {
                v.setMessage("Bạn chưa nhận voucher này");
                v.setIsHaveSlot(0);
                v.setIsGain(0);
                v.setCanApply(false);
            } else {
                v.setIsGain(1);
                v.setIsHaveSlot(
                        v.getQuantityPer() > quantityUsed ? v.getLimitUsage() > v.getQuantityUsed() ? 1 : 0 : 0);
                v.setCanApply(v.getIsHaveSlot() != 0 ? true : false);
                if (v.getIsHaveSlot() == 0) {
                    v.setMessage("Không còn lượt sử dụng");
                }
                if (v.getCanApply()) {
                    caculateDiscountFee(sumaryProduct, v);
                }
            }
        });
        return vouchers;
    }

    public String checkVoucherIsActive(Integer voucherId) {
        return voucherRepo.checkVoucherIsActive(voucherId);
    }

    public Integer checkIsGain(Integer voucherId) {
        return voucherRepo.checkVoucherIsGain(voucherId, shopBean.getAccount().getId());
    }

    public void claimVoucher(VoucherShopAccount v) {
        voucherShopAccountRepo.save(v);
    }

    private void caculateDiscountFee(Map<Integer, Double> productItems, VoucherApllyInfo v) {
        Double amount = 0.0;
        Integer quantity = 0;
        for (Integer productIds : v.getProductIds()) {
            Double p = productItems.get(productIds);
            if (p != null) {
                quantity += 1;
                amount += p;
            }
        }
        if (quantity < 1) {
            v.setCanApply(false);
            v.setMessage("Chưa có sản phẩm yêu cầu của voucher");
        } else if (v.getMinimumOrder() > amount) {
            v.setCanApply(false);
            v.setMessage("Mua thêm " + (v.getMinimumOrder() - amount) + " để giảm voucher");
        } else {
            Double discountValue = 0.0;
            if (v.getVoucherType().equals("PERCENT")) {
                discountValue = amount * (v.getDiscountValue() / 100);
            } else {
                discountValue = amount;
            }
            v.setPriceExpectReduce(amount > discountValue ? discountValue : amount);
        }
    }

    public Map<Integer, Integer> getVoucherGain(List<Integer> voucherIds) {
        List<VoucherShopAccount> voucherShopAccounts = voucherRepo.getVoucherShopAccount(shopBean.getAccount().getId(),
                voucherIds);
        Map<Integer, Integer> voucherMap = new HashMap<>();
        voucherShopAccounts.forEach(v -> {
            voucherMap.put(v.getId().getVoucherId(), v.getQuantityUsed());
        });
        return voucherMap;
    }

}