package com.yatrago.backend.auth.services;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.yatrago.backend.auth.dto.RefreshTokenDTO;
import com.yatrago.backend.auth.dto.SocialLoginDTO;
import com.yatrago.backend.auth.dto.SocialLoginDTO.UserDTO;
import com.yatrago.backend.auth.providers.SocialAuthProvider;
import com.yatrago.backend.entity.UsersEntity;
import com.yatrago.backend.exceptions.AuthenticationException;
import com.yatrago.backend.repositories.UserRepository;
import com.yatrago.backend.utility.Utils;

@Service
public class AuthenticationService {

    Map<String, SocialAuthProvider> providers;

    UserRepository userRepository;

    JWTService jwtService;

    public SocialLoginDTO.ResponseDTO getResponse(SocialLoginDTO.RequestDTO socialLoginRequest){

        //Select Provider (Google/Facebook)
        SocialAuthProvider provider = providers.get(socialLoginRequest.getSocialProvider().toString());

        if(provider == null){
            throw new AuthenticationException("Invalid Provider");
        }
        
        //Verify social Token

        Map<String, String> result = provider.authenticate(socialLoginRequest.getToken());

        //Find and create User
        UsersEntity user = userRepository.findByEmail(result.get("email"));
        if(user == null){
            UsersEntity newUser = new UsersEntity();
            newUser.setEmail(user.getEmail());
            newUser.setProvider(socialLoginRequest.getSocialProvider());
            newUser.setProviderUserId(result.get("providerId"));
            user = userRepository.save(newUser);
        }
        
        //Generate JWTs
        String accessToken = jwtService.generateAccessToken(user);

        //Save Refresh Token
        String refreshToken = jwtService.generateRefreshToken(user);

        // Create a user DTO
        UserDTO userDTO = UserDTO.builder().userId(user.getId()).firstName(user.getFirstName())
        .middleName(user.getMiddleName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .countryCode(user.getCountryCode())
        .mobileNumber(user.getMobileNumber())
        .build();


        //Return response
        return new SocialLoginDTO.ResponseDTO(false, "Token generated successfully.", userDTO, refreshToken, accessToken);
    }

    public RefreshTokenDTO.ResponseDTO refreshToken(RefreshTokenDTO.RequestDTO requestDTO){

        String email = jwtService.validateJWTToken(requestDTO.getRefreshToken());
        if(Utils.isNullOrEmpty(email)){
            return null;
        }
        UsersEntity user = userRepository.findByEmail(email);
        String accessToken = jwtService.generateAccessToken(user);

        return new RefreshTokenDTO.ResponseDTO(accessToken);

    }

    public SocialLoginDTO.UserDTO getUser(String userId){
        try{
            UUID uid = UUID.fromString(userId);
            UsersEntity user = userRepository.findById(uid).orElseThrow();
            SocialLoginDTO.UserDTO userDTO = UserDTO.builder().userId(user.getId()).firstName(user.getFirstName()).middleName(user.getMiddleName())
            .lastName(user.getLastName())
            .email(user.getEmail())
            .countryCode(user.getCountryCode())
            .mobileNumber(user.getMobileNumber())
            .build();
            return userDTO;
        }
        catch(Exception e){
            return null;
        }
    }
}
