package com.yatrago.backend.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yatrago.backend.auth.dto.RefreshTokenDTO;
import com.yatrago.backend.auth.dto.SocialLoginDTO;
import com.yatrago.backend.auth.services.AuthenticationService;

@RestController
@RequestMapping("/api/v1/driver/auth")
public class DriverAuthController {

    @Autowired
    AuthenticationService authenticationImplementation;

    /**
     * Once the user Login using the social account then   we will generate the access and refresh token
     * 
     * @return
     */
    @PostMapping("/social-login")
    public ResponseEntity<SocialLoginDTO.ResponseDTO> socialLoginOrRegisterUser(@RequestBody SocialLoginDTO.RequestDTO socialLoginRequest){
        SocialLoginDTO.ResponseDTO response = authenticationImplementation.getResponse(socialLoginRequest);

        ResponseCookie accessCookie = ResponseCookie.from("accessToken", response.getAccessToken()).httpOnly(true).secure(true).path("/").maxAge(15*60).sameSite(null).build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", response.getRefreshToken()).httpOnly(true).secure(true).path("/").maxAge(15*60).sameSite(null).build();

        return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
        .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
        .body(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenDTO.ResponseDTO>  refreshToken(@CookieValue("refreshToken") String refreshToken){
        RefreshTokenDTO.ResponseDTO response = authenticationImplementation.refreshToken(new RefreshTokenDTO.RequestDTO(refreshToken));

        ResponseCookie accessCookie = ResponseCookie.from("accessToken", response.getAccessToken()).httpOnly(true).secure(true).path("/").maxAge(15*60).sameSite(null).build();

        return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
        .body(response);

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser(){
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", "")
            .httpOnly(true)
            .secure(true)
            .path("/")
            .sameSite("Strict")
            .maxAge(0)
            .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", "")
            .httpOnly(true)
            .secure(true)
            .path("/")
            .sameSite("Strict")
            .maxAge(0)
            .build();

        return ResponseEntity.noContent()
            .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
            .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
            .build();
    }

    @GetMapping("/user")
    public ResponseEntity<SocialLoginDTO.UserDTO> getUserFromId(@RequestParam String userId){
        SocialLoginDTO.UserDTO userDTO = authenticationImplementation.getUser(userId);
        return ResponseEntity.ok().body(userDTO);
    }
    
}
