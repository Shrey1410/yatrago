package com.yatrago.backend.auth.dto;

import java.util.UUID;

import com.yatrago.backend.common.CommonEnums;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class SocialLoginDTO {
    
    @Getter
    @Setter
    @AllArgsConstructor
    public static class RequestDTO {

        private CommonEnums.SocialProvider socialProvider;
        private String token;
        
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class ResponseDTO {

        private Boolean success;
        private String message;
        private UserDTO user;
        private String refreshToken;
        private String accessToken;

    }

    @Setter
    @Getter
    @Builder
    public static class UserDTO {

        UUID userId;
        String firstName;
        String middleName;
        String lastName;
        String email;
        String countryCode;
        String mobileNumber;

    }


}
