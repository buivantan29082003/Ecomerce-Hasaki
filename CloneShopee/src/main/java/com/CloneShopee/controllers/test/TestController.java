package com.CloneShopee.controllers.test;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.CloneShopee.ResponeEntity.BaseRespone;
import com.CloneShopee.models.Order;
import com.CloneShopee.models.ProductImage;
import com.CloneShopee.services.Common.PaymentVNPayService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import io.jsonwebtoken.io.IOException;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.io.Console;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin("*")
public class TestController {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    PaymentVNPayService vnpayService;

    @GetMapping("/vnpay/ipn")
    public ResponseEntity<Object> getVnpayIpn(@RequestParam Map<String, String> allParams) {
        System.out.println("===== VNPAY IPN CALLBACK RECEIVED =====");

        // In ra toàn bộ tham số VNPay gửi về
        for (Map.Entry<String, String> entry : allParams.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

        // Lấy từng trường cụ thể để tiện xử lý
        String vnp_TmnCode = allParams.get("vnp_TmnCode");
        String vnp_Amount = allParams.get("vnp_Amount");
        String vnp_BankCode = allParams.get("vnp_BankCode");
        String vnp_BankTranNo = allParams.get("vnp_BankTranNo");
        String vnp_CardType = allParams.get("vnp_CardType");
        String vnp_OrderInfo = allParams.get("vnp_OrderInfo");
        String vnp_PayDate = allParams.get("vnp_PayDate");
        String vnp_ResponseCode = allParams.get("vnp_ResponseCode");
        String vnp_TransactionNo = allParams.get("vnp_TransactionNo");
        String vnp_TransactionStatus = allParams.get("vnp_TransactionStatus");
        String vnp_TxnRef = allParams.get("vnp_TxnRef");
        String vnp_SecureHash = allParams.get("vnp_SecureHash");

        System.out.println(">>> Transaction Info <<<");
        System.out.println("Mã website (vnp_TmnCode): " + vnp_TmnCode);
        System.out.println("Số tiền (vnp_Amount): " + vnp_Amount);
        System.out.println("Ngân hàng (vnp_BankCode): " + vnp_BankCode);
        System.out.println("Mã giao dịch ngân hàng (vnp_BankTranNo): " + vnp_BankTranNo);
        System.out.println("Loại thẻ (vnp_CardType): " + vnp_CardType);
        System.out.println("Nội dung đơn hàng (vnp_OrderInfo): " + vnp_OrderInfo);
        System.out.println("Thời gian thanh toán (vnp_PayDate): " + vnp_PayDate);
        System.out.println("Mã phản hồi (vnp_ResponseCode): " + vnp_ResponseCode);
        System.out.println("Mã giao dịch VNPAY (vnp_TransactionNo): " + vnp_TransactionNo);
        System.out.println("Trạng thái giao dịch (vnp_TransactionStatus): " + vnp_TransactionStatus);
        System.out.println("Mã đơn hàng (vnp_TxnRef): " + vnp_TxnRef);
        System.out.println("Chữ ký (vnp_SecureHash): " + vnp_SecureHash);
        System.out.println("=========================================");

        // TODO: Thực hiện xác minh checksum ở đây nếu cần (bắt buộc trong thực tế)
        // boolean isValid = verifyChecksum(allParams);
        // if (!isValid) return ResponseEntity.ok(Map.of("RspCode", "97", "Message",
        // "Invalid checksum"));

        // Kiểm tra kết quả thanh toán
        if ("00".equals(vnp_ResponseCode) && "00".equals(vnp_TransactionStatus)) {
            System.out.println(">>> Thanh toán thành công!");
            // TODO: cập nhật DB, đổi trạng thái đơn hàng...
            return ResponseEntity.ok(Map.of("RspCode", "00", "Message", "Confirm Success"));
        } else {
            System.out.println(">>> Thanh toán thất bại hoặc bị hủy!");
            return ResponseEntity.ok(Map.of("RspCode", "01", "Message", "Transaction Failed"));
        }
    }

    @GetMapping("test/createpayment")
    public ResponseEntity<Object> createOrder() {
        Order o = new Order();
        o.setTotalAmount(30000.0);
        Object payment = vnpayService.createOrder(o);
        return new ResponseEntity<>(new BaseRespone(payment, "succcess"), HttpStatus.OK);
    }

    @PostMapping("/upload-multiple-files")
    public BaseRespone uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) throws java.io.IOException {
        List<ProductImage> productImages = new ArrayList<ProductImage>();
        for (MultipartFile file : files) {
            try {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                productImages.add(new ProductImage(uploadResult.get("url").toString()));
                // String url = uploadResult.get("url").toString();
            } catch (IOException e) {
                return new BaseRespone(null, "failes");
            }
        }

        return new BaseRespone(productImages, "success");
    }

    @PostMapping("/upload-single-files")
    public BaseRespone uploadSingleFile(@RequestParam("files") MultipartFile file) throws java.io.IOException {
        String url;
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            // Lấy URL từ kết quả trả về
            url = uploadResult.get("url").toString();
            new BaseRespone(url, "success");
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseRespone(null, "failes");
        }
        return new BaseRespone(url, "success");
    }

    @PostMapping("/upload-single-files-video")
    public BaseRespone uploadSingleFileVideo(@RequestParam("files") MultipartFile file) throws java.io.IOException {
        String url;
        try {
            // Kiểm tra nếu tệp rỗng
            if (file.isEmpty()) {
                return new BaseRespone(null, "File is empty");
            }

            // Upload file lên Cloudinary với resource_type là video
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("resource_type", "video")); // Chỉ định resource_type là video

            // Lấy URL từ kết quả trả về
            url = uploadResult.get("url").toString();
            return new BaseRespone(url, "success"); // Trả về URL
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseRespone("failed", "error");
        }
    }

}