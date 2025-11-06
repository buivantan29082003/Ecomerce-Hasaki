package com.CloneShopee.services.Common;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.stereotype.Service;

import com.CloneShopee.config.Payment;
import com.CloneShopee.models.Order;
import com.CloneShopee.models.Transaction;
import com.CloneShopee.services.ServiceInterface.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PaymentVNPayService implements PaymentService {

    @Override
    public Object createOrder(Order order) {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "billpayment";
        // String vnp_TxnRef = String.valueOf(System.currentTimeMillis());
        String vnp_IpAddr = "127.0.0.1"; // IPv4, không dùng localhost

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", Payment.timeCode);
        vnp_Params.put("vnp_Amount", String.valueOf((long) (order.getTotalAmount() * 100)));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", order.getId() + "");
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", Payment.returnFeUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_OrderInfo", "" + order.getId());

        // Thêm vnp_CreateDate và vnp_ExpireDate
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // Sắp xếp theo thứ tự alphabet
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (int i = 0; i < fieldNames.size(); i++) {
            String fieldName = fieldNames.get(i);
            String fieldValue = vnp_Params.get(fieldName);

            if (fieldValue != null && !fieldValue.isEmpty()) {
                try {
                    // Encode UTF-8 và thay %20 thành +
                    String encodedValue = URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString())
                            .replace("%20", "+");

                    hashData.append(fieldName).append("=").append(encodedValue);
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8.toString()))
                            .append("=")
                            .append(encodedValue);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                if (i < fieldNames.size() - 1) {
                    hashData.append("&");
                    query.append("&");
                }
            }
        }

        // Tạo chữ ký HMAC SHA512
        String vnp_SecureHash = HmacUtils.hmacSha512Hex(Payment.secret, hashData.toString());

        // Thêm Secure Hash vào URL
        query.append("&vnp_SecureHashType=HMACSHA512");
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);

        String queryUrl = query.toString();
        String paymentUrl = Payment.vnpayUrl + "?" + queryUrl;

        System.out.println("==== HASH DATA ====");
        System.out.println(hashData);
        System.out.println("==== PAYMENT URL ====");
        System.out.println(paymentUrl);

        return paymentUrl;
    }

    @Override
    public Object refundOrder(Order order, Transaction transaction) {
        try {
            String vnp_RequestId = String.valueOf(System.currentTimeMillis());
            String vnp_Version = "2.1.0";
            String vnp_Command = "refund";
            String vnp_TmnCode = Payment.timeCode;
            String vnp_TransactionType = "02"; // 02 = hoàn toàn phần, 03 = hoàn 1 phần
            String vnp_TxnRef = String.valueOf(order.getId());
            String vnp_Amount = String.valueOf((long) (order.getTotalAmount() * 100)); // nhân 100
            String vnp_OrderInfo = "Hoàn tiền cho đơn hàng " + order.getId();
            String vnp_CreateBy = "SYSTEM"; // user thực hiện refund
            String vnp_IpAddr = "127.0.0.1";
            String vnp_TransactionNo = transaction.getTransactionId() + "";

            // Thời gian giao dịch gốc (bạn nên lưu lúc thanh toán)
            String vnp_TransactionDate = new SimpleDateFormat("yyyyMMddHHmmss")
                    .format(order.getCreatedDate());

            // Thời gian phát sinh refund
            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            String vnp_CreateDate = new SimpleDateFormat("yyyyMMddHHmmss").format(cld.getTime());

            // ====== Tạo data string để hash ======
            String data = vnp_RequestId + "|" + vnp_Version + "|" + vnp_Command + "|" + vnp_TmnCode + "|"
                    + vnp_TransactionType + "|" + vnp_TxnRef + "|" + vnp_Amount + "|" + vnp_TransactionNo + "|"
                    + vnp_TransactionDate + "|" + vnp_CreateBy + "|" + vnp_CreateDate + "|" + vnp_IpAddr + "|"
                    + vnp_OrderInfo;

            // ====== Sinh SecureHash (HMAC SHA512) ======
            String vnp_SecureHash = HmacUtils.hmacSha512Hex(Payment.secret, data);

            // ====== Tạo body JSON ======
            Map<String, String> requestData = new HashMap<>();
            requestData.put("vnp_RequestId", vnp_RequestId);
            requestData.put("vnp_Version", vnp_Version);
            requestData.put("vnp_Command", vnp_Command);
            requestData.put("vnp_TmnCode", vnp_TmnCode);
            requestData.put("vnp_TransactionType", vnp_TransactionType);
            requestData.put("vnp_TxnRef", vnp_TxnRef);
            requestData.put("vnp_Amount", vnp_Amount);
            requestData.put("vnp_TransactionNo", vnp_TransactionNo);
            requestData.put("vnp_TransactionDate", vnp_TransactionDate);
            requestData.put("vnp_CreateBy", vnp_CreateBy);
            requestData.put("vnp_CreateDate", vnp_CreateDate);
            requestData.put("vnp_IpAddr", vnp_IpAddr);
            requestData.put("vnp_OrderInfo", vnp_OrderInfo);
            requestData.put("vnp_SecureHash", vnp_SecureHash);

            // ====== Gửi POST request ======
            String apiUrl = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(requestData)))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("===== REFUND REQUEST =====");
            System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(requestData));
            System.out.println("===== REFUND RESPONSE =====");
            System.out.println(response.body());

            // ====== Phân tích kết quả ======
            Map<String, Object> responseMap = new ObjectMapper().readValue(response.body(), Map.class);
            String responseCode = (String) responseMap.get("vnp_ResponseCode");

            if ("00".equals(responseCode)) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Object payOrder(Order order) {
        throw new UnsupportedOperationException("Unimplemented method 'payOrder'");
    }

    @Override
    public Object getPaymentInfo(Order order) {
        throw new UnsupportedOperationException("Unimplemented method 'getPaymentInfo'");
    }

}
