package com.yatrago.backend.auth.providers;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import com.yatrago.backend.common.CommonEnums;
import com.yatrago.backend.exceptions.AuthenticationException;
import com.yatrago.backend.utility.Utils;

@Component
public class GoogleAuthProvider implements SocialAuthProvider {
    
    @Value("${google.client-id}")
    private String clientId;

    @Override
    public CommonEnums.SocialProvider getSocialProvider(){
        return CommonEnums.SocialProvider.GOOGLE;
    }

    @Override 
    public Map<String, String> authenticate(String token) throws AuthenticationException{
        if(!Utils.isNullOrEmpty(token)){
            throw new AuthenticationException("Google auth token is absent.");
        }
        try{
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance()).setAudience(Collections.singletonList(clientId)).build();

            GoogleIdToken idToken = verifier.verify(token);

            if(idToken == null){
                throw new AuthenticationException("Invalid google auth token.");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();

            if(!Boolean.TRUE.equals(payload.getEmailVerified())){
                throw new AuthenticationException("User email is not verified.");
            }

            Map<String, String> payloadMap = new HashMap<>();
            payloadMap.put("email", payload.getEmail());
            payloadMap.put("providerId", payload.getSubject());

            return payloadMap;
        }
        catch(Exception e){
            throw new AuthenticationException("Error while Authentication");
        }
    }
    
}
