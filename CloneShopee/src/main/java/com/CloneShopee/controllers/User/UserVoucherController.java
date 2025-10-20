package com.CloneShopee.controllers.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CloneShopee.Bean.ShopBean;
import com.CloneShopee.Bean.UserBean;
import com.CloneShopee.DTO.User.VoucherApllyInfo;
import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.models.VoucherShop;
import com.CloneShopee.models.VoucherShopAccount;
import com.CloneShopee.models.VoucherShopAccountId;
import com.CloneShopee.services.User.VoucherServiceUser;
import com.CloneShopee.services.sale.VoucherService;

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin("*")
public class UserVoucherController {

    @Autowired
    UserBean userBean;

    @Autowired
    ShopBean shopBean;

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private VoucherServiceUser voucherServiceUser;

    @PostMapping("/user/voucher/allvoucherrecommend")
    public ResponseEntity<Object> getAllVoucherRecommendProject(
            @RequestBody Map<Integer, Double> productMap) {
        List<VoucherApllyInfo> v = voucherServiceUser.getAllVoucherByProduct(productMap);
        return new ResponseEntity<>(new BaseRespone(v, "success"), HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/user/voucher/add")
    public ResponseEntity<Object> reciveVoucher(@RequestParam("voucherId") Integer voucherId) {
        VoucherShop voucher = voucherService.getVoucherByIdAndStarting(voucherId);
        if (voucher != null) {
            VoucherShopAccount voucherShopAccount = new VoucherShopAccount();
            voucherShopAccount.setQuantityUsed(0);
            voucherShopAccount.setId(new VoucherShopAccountId(voucherId, userBean.getId()));

            return new ResponseEntity<>(new BaseRespone(null, "Success !!!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseRespone(null, "Not find voucher !!!"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("user/voucher/claim/common")
    public ResponseEntity<Object> claimVoucherCommon(
            @RequestParam(value = "voucherId", required = false) Integer voucherId) {
        String style = voucherServiceUser.checkVoucherIsActive(voucherId);
        if (style != null) {
            if (!style.equals("LIVE")) {
                Integer a = voucherServiceUser.checkIsGain(voucherId);
                if (a == null) {
                    VoucherShopAccount v = new VoucherShopAccount();
                    v.setId(new VoucherShopAccountId(voucherId, shopBean.getAccount().getId()));
                    v.setQuantityUsed(0);
                    voucherServiceUser.claimVoucher(v);
                }
                return new ResponseEntity<>(new BaseRespone(null, "success"),
                        HttpStatus.OK);
            }
            return new ResponseEntity<>(new BaseRespone(null, "Voucher chỉ được nhận trực tiếp trong live"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new BaseRespone(null, "Không thể nhận voucher"), HttpStatus.BAD_REQUEST);
    }

}
