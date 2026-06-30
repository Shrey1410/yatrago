package com.yatrago.backend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class RefreshTokenDTO {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class RequestDTO {

        private String refreshToken;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ResponseDTO {

        private String accessToken;

    }
    
}
