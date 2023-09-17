package com.picpay.picpaydesafio.auth;

import javax.servlet.http.HttpServletRequest;

public class TokenUtils {

    public static String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7, token.length());
    }
}
