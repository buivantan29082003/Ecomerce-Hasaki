package com.CloneShopee.config;

import java.util.Base64;
import org.springframework.stereotype.Component;

@Component
public class Base64PasswordService {

    // Mã hóa chuỗi thành Base64
    public String encode(String rawPassword) {
        return Base64.getEncoder().encodeToString(rawPassword.getBytes());
    }

    // Giải mã Base64 về chuỗi ban đầu
    public String decode(String encodedPassword) {
        return new String(Base64.getDecoder().decode(encodedPassword));
    }
}
