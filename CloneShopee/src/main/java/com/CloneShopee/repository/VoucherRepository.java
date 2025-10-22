package com.CloneShopee.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CloneShopee.DTO.Sale.Voucher.VoucherInfo;
import com.CloneShopee.DTO.User.VoucherApllyInfo;
import com.CloneShopee.DTO.User.product.VoucherDetail;
import com.CloneShopee.models.VoucherBuyBack;
import com.CloneShopee.models.VoucherCommon;
import com.CloneShopee.models.VoucherLive;
import com.CloneShopee.models.VoucherShop;
import com.CloneShopee.models.VoucherShopAccount;

public interface VoucherRepository extends JpaRepository<VoucherShop, Integer>, JpaSpecificationExecutor<VoucherShop> {

  @Query("select p from VoucherLive p where p.id=:voucherId and p.isActive=1 and (p.limitUsage=-1 or p.limitUsage>p.quantityUsed)")
  VoucherLive getVoucherLiveByVoucherId(@Param("voucherId") Integer voucherId);

  @Query("select p from VoucherCommon p where p.id=:voucherId and p.isActive=1 and (p.limitUsage=-1 or p.limitUsage>p.quantityUsed)")
  VoucherCommon getVoucherCommonByVoucherId(@Param("voucherId") Integer voucherId);

  @Query("select p.voucherStyle from VoucherShop p where p.id=:voucherId and p.isActive=1 and now() between p.startDate and p.endDate")
  public String checkVoucherIsActive(@Param("voucherId") Integer voucherId);

  @Query("select p.id.accountId from VoucherShopAccount p where p.id.voucherId=:voucherId and p.id.accountId=:accountId")
  Integer checkVoucherIsGain(@Param("voucherId") Integer voucherId, @Param("accountId") Integer accountId);

  @Query("select new com.CloneShopee.models.VoucherShopAccount(p.id.voucherId,p.quantityUsed) from VoucherShopAccount p where p.id.accountId=:accountId and p.id.voucherId in:voucherIds")
  List<VoucherShopAccount> getVoucherShopAccount(@Param("accountId") Integer accountId,
      @Param("voucherIds") List<Integer> voucherIds);

  @Query("select new com.CloneShopee.DTO.User.VoucherApllyInfo(p) from VoucherShop  p where  p.isActive=1 and now() between p.startDate and p.endDate")
  List<VoucherApllyInfo> getAllVoucherByProductIds();

  @Query("select new com.CloneShopee.models.VoucherShop(p.id,p.voucherName,p.voucherCode,p.startDate,p.endDate,p.voucherType,p.discountValue,null,p.totalProduct,p.voucherStyle) from VoucherShop p where p.shop.id=:shopId and now() between p.startDate and p.endDate and p.isActive=1")
  List<VoucherShop> getAllVoucherByShopId(@Param("shopId") Integer shopId);

  @Query("select new com.CloneShopee.DTO.User.product.VoucherDetail(p.voucherShop.id,p.voucherShop.voucherName,p.voucherShop.voucherStyle,p.voucherShop.discountValue,p.voucherShop.voucherType,p.voucherShop.minimumPurchase,1) from VoucherShopItem p where p.product.id=:productId and p.voucherShop.isActive=1 and now() between p.voucherShop.startDate and p.voucherShop.endDate")
  List<VoucherDetail> getVoucherDetailByProductId(@Param("productId") Integer productId);

  @Query("""
          SELECT new com.CloneShopee.DTO.Sale.Voucher.VoucherInfo(
              v.id,
              v.voucherName,
              v.voucherCode,
              v.startDate,
              v.endDate,
              v.isActive,
              v.totalProduct,
              v.voucherType,
              v.voucherStyle
          )
          FROM VoucherShop v
          WHERE (v.voucherName LIKE :key) AND (:ids is null or v.id in :ids)
            AND (
              :status IS NULL
              OR (:status = 1 AND CURRENT_TIMESTAMP BETWEEN v.startDate AND v.endDate)
              OR (:status = 2 AND v.startDate BETWEEN CURRENT_TIMESTAMP AND (CURRENT_TIMESTAMP + 1 DAY))
              OR (:status = 3 AND v.isActive = 0)
              OR (:status = 4 AND v.endDate < CURRENT_TIMESTAMP)
            )
      """)
  Page<VoucherInfo> findAllParentOnly(
      @Param("key") String key,
      @Param("status") Integer status,
      Pageable pageable, @Param("ids") List<Integer> l);

  @Query(value = "SELECT COUNT(p.id) AS countVoucher, " +
      " SUM(p.totalAmount) AS tongTienDon, " +
      " SUM(p.totalDiscountVoucher) AS totalDiscountVoucher, " +
      " COUNT(DISTINCT p.accountId) AS TongSoKhachHang " +
      " FROM orders p " +
      " WHERE p.voucherShopId IS NOT NULL " +
      " AND p.createdDate >= DATE_SUB(CURDATE(), INTERVAL :beforeDate DAY)", nativeQuery = true)
  Object getOveralVoucher(@Param("beforeDate") Integer beforeDate);

  @Query("select p.voucherShop.id from VoucherShopItem p where p.product.productName like:productName")
  public List<Integer> getVoucherIdsByProductName(@Param("productName") String productName);

  @Query("select p.id from VoucherShop p where p.shop.id=:shopId and p.voucherCode=:voucherCode")
  Integer getVoucherIdByVoucherCodeAndShopId(@Param("shopId") Integer shopId,
      @Param("voucherCode") String voucherCode);

  @Query("select p from VoucherShop p join VoucherShopAccount p1 where p.id=p1.id.voucherId and p1.id.accountId=:accountId and p.shop.id=:shopId and NOW() between p.startDate and p.endDate")
  List<VoucherShop> getVoucherOfShopAndAccountReciveAndStarting(@Param("shopId") Integer shopId,
      @Param("accountId") Integer accountId);

  @Query("SELECT p from VoucherShop p where p.id=:voucherId and now() BETWEEN p.startDate AND p.endDate")
  public VoucherShop getVoucherByIdAndStarting(@Param("voucherId") Integer voucherId);

  @Query("SELECT p.id FROM VoucherShop p WHERE p.id=:id and p.shop.id=:shopId")
  Optional<Integer> getVoucherByIdAndShopId(@Param("id") Integer id, @Param("shopId") Integer shopId);

  @Modifying
  @Query("DELETE VoucherShopItem p where p.product.id in: productIds")
  void deleteVoucherItemInProductIds(@Param("productIds") Set<Integer> productIds);

  @Modifying
  @Query("UPDATE VoucherShop p set p.isActive=:isActive where p.id=:id")
  void updateStatusVoucherByVoucherId(@Param("voucherId") Integer voucherId,
      @Param("isActive") Integer isActive);

  @Modifying
  @Query("UPDATE VoucherShop v SET v.voucherName = :voucherName, v.startDate = :startDate, v.endDate = :endDate, v.voucherType = :voucherType, v.discountValue = :discountValue, v.limitUsage = :limitUsage, v.limitValue = :limitValue, v.isActive = :isActive WHERE v.id = :id")
  int updateVoucherShop(@Param("id") Integer id, @Param("voucherName") String voucherName,
      @Param("startDate") Date startDate, @Param("endDate") Date endDate,
      @Param("voucherType") String voucherType, @Param("discountValue") Double discountValue,
      @Param("limitUsage") Integer limitUsage, @Param("limitValue") Double limitValue,
      @Param("isActive") Integer isActive);

  @Query("""
      SELECT new com.CloneShopee.models.VoucherShop(vs.id, vs.discountValue, vs.voucherType,vs.minimumPurchase)
      FROM VoucherShop vs
      JOIN VoucherShopAccount vc
          ON vc.id.voucherId = vs.id
      WHERE vs.id = :voucherId
        AND vc.id.accountId = :accountId
        AND vs.shop.id = :shopId and vc.quantityUsed<vs.limitUsage AND vs.voucherStyle=:voucherStyle AND now() between vs.startDate and vs.endDate
      """)
  public Optional<VoucherShop> getVoucherShopIsApply(
      @Param("voucherId") Integer voucherId,
      @Param("shopId") Integer shopId,
      @Param("accountId") Integer userId, @Param("voucherStyle") String voucherStyle);

  @Query("""
      SELECT new com.CloneShopee.models.VoucherBuyBack(vs.id, vs.discountValue, vs.voucherType,vs.minimumPurchase,vs.latestOrderDays)
      FROM VoucherBuyBack vs
      JOIN VoucherShopAccount vc
          ON vc.id.voucherId = vs.id
      WHERE vs.id = :voucherId
        AND vc.id.accountId = :accountId
        AND vs.shop.id = :shopId and vc.quantityUsed<vs.limitUsage AND vs.voucherStyle=:voucherStyle AND now() between vs.startDate and vs.endDate
      """)
  public Optional<VoucherBuyBack> getVoucherShopBuyBackIsApply(
      @Param("voucherId") Integer voucherId,
      @Param("shopId") Integer shopId,
      @Param("accountId") Integer userId, @Param("voucherStyle") String style);

  @Modifying
  @Query("UPDATE VoucherShopAccount vc SET vc.quantityUsed=vc.quantityUsed+1 where vc.id.voucherId=:voucherId and vc.id.accountId=:accountId and vc.quantityUsed+1<=:quantityPer")
  public Integer updateQuantityUsed(@Param("voucherId") Integer voucherId, @Param("accountId") Integer accountId,
      @Param("quantityPer") Integer quantityPer);

  @Modifying
  @Query("update VoucherShop p set p.quantityUsed=p.quantityUsed+1 where p.id=:voucherId and p.quantityUsed+1<=limitUsage ")
  public Integer updateQuantityUsedOfVoucherByVoucherId(@Param("voucherId") Integer voucherId);

  @Query("SELECT p.product.id from VoucherShopItem p where p.voucherShop.id=:voucherId")
  Set<Integer> getVoucherIdIdProduct(@Param("voucherId") Integer voucherId);

  @Query("select new com.CloneShopee.models.VoucherShopAccount(p.quantityUsed) from VoucherShopAccount p where p.id.accountId=:accountId and p.id.voucherId=:voucherId")
  public VoucherShopAccount getVoucherGainedByAccountIdAndvoucherId(@Param("accountId") Integer accountId,
      @Param("voucherId") Integer voucherId);

}
