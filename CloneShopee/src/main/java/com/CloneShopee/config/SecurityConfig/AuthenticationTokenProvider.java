package com.CloneShopee.config.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.CloneShopee.Utils.TokenUtil;
import com.CloneShopee.config.Base64PasswordService;
import com.CloneShopee.services.AuthService;

@Service
public class AuthenticationTokenProvider implements AuthenticationProvider {

    @Autowired
    AuthService auth;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    Base64PasswordService base64Service;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AuthenticationCustome auth = (AuthenticationCustome) authentication;
        // if (auth.getToken() != null) {
        // System.out.println(auth.getToken());
        // tokenUtil.generateToken();
        // xác thực ở đây\
        auth.setAuthenticated(true);
        System.out.println(auth.isAuthenticated());
        // }
        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(AuthenticationCustome.class);
    }

}
