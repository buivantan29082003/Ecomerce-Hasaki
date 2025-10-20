package com.CloneShopee.services;

import com.CloneShopee.models.Order;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@CrossOrigin("*")
@RestController
public class GHNService {
    private final String TOKEN = "1c0642bd-4891-11ef-af01-5a4abb38d4d4";
    private final String SHOP_ID = "5146217";

    // ======================== LẤY DANH SÁCH TỈNH ========================
    @GetMapping("/provinces")
    public ResponseEntity<Object> getProvinces() {
        String url = "https://online-gateway.ghn.vn/shiip/public-api/master-data/province";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("token", TOKEN);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Map.class);

        return ResponseEntity.ok(response.getBody());
    }

    // ======================== LẤY QUẬN/HUYỆN ========================
    @GetMapping("/districts")
    public ResponseEntity<Object> getDistricts(@RequestParam("provinceId") int provinceId) {
        String url = "https://online-gateway.ghn.vn/shiip/public-api/master-data/district";
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> body = new HashMap<>();
        body.put("province_id", provinceId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("token", TOKEN);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    Map.class);

            Object data = response.getBody() != null ? response.getBody().get("data") : null;

            return ResponseEntity.ok(Map.of(
                    "code", response.getStatusCodeValue(),
                    "data", data));
        } catch (HttpClientErrorException.Unauthorized ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "GHN token invalid", "detail", ex.getResponseBodyAsString()));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    // ======================== LẤY PHƯỜNG/XÃ ========================
    private static final String WARD_URL = "https://online-gateway.ghn.vn/shiip/public-api/master-data/ward";

    @GetMapping("/wards")
    public ResponseEntity<Object> getWards(@RequestParam("districtId") int districtId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = WARD_URL + "?district_id=" + districtId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("token", TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("district_id", districtId);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Object> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    Object.class);
            return response;
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    // lấy dịch vụ

    @GetMapping("/available-services")
    public ResponseEntity<Object> getAvailableServices(
            @RequestParam("fromDistrict") int fromDistrict,
            @RequestParam("toDistrict") int toDistrict) {

        String url = "https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/available-services";

        RestTemplate restTemplate = new RestTemplate();

        // Header
        HttpHeaders headers = new HttpHeaders();
        headers.set("token", TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Body
        Map<String, Object> body = new HashMap<>();
        body.put("shop_id", 885);
        body.put("from_district", fromDistrict);
        body.put("to_district", toDistrict);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    Map.class);

            // GHN trả về list "data" chứa danh sách dịch vụ
            Object data = response.getBody() != null ? response.getBody().get("data") : null;

            return ResponseEntity.ok(Map.of(
                    "code", response.getStatusCodeValue(),
                    "data", data));
        } catch (HttpClientErrorException.Unauthorized ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Token không hợp lệ", "detail", ex.getResponseBodyAsString()));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    // ======================== TÍNH PHÍ SHIP ========================
    private static final String GHN_FEE_URL = "https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee";

    /**
     * Tính phí vận chuyển dựa theo thông tin đơn hàng
     */
    public Double callFeeShipOfOrder(Order order) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            Map<String, Object> body = new HashMap<>();
            body.put("from_district_id", 3445); // Quận của shop
            body.put("from_ward_code", "370907");
            body.put("service_id", 53320);
            body.put("service_type_id", 2);
            body.put("to_district_id", 1572);
            body.put("to_ward_code", "550113");
            body.put("height", 20);
            body.put("length", 20);
            body.put("weight", 200);
            body.put("width", 20);
            body.put("insurance_value", 10000);
            body.put("cod_failed_amount", 0);
            body.put("coupon", null);

            // Items (nếu có)

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Token", TOKEN);
            headers.set("ShopId", SHOP_ID);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    GHN_FEE_URL,
                    HttpMethod.POST,
                    entity,
                    Map.class);

            Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
            if (data != null && data.get("total") != null) {
                return Double.valueOf(data.get("total").toString());
            }
            return 0.0;

        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    // ======================== TEST API ========================
    @PostMapping("/testFee")
    public ResponseEntity<Object> testFee() {
        Double fee = callFeeShipOfOrder(null);
        return ResponseEntity.ok(Map.of("fee", fee));
    }
}
