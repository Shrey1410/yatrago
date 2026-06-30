package com.yatrago.backend.auth.providers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.yatrago.backend.common.CommonEnums;
import com.yatrago.backend.exceptions.AuthenticationException;
import com.yatrago.backend.utility.Utils;

import lombok.Data;

public class FacebookAuthProvider implements SocialAuthProvider{

    @Value("${app}")
    private String appId;

    @Value("")
    private String appSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public CommonEnums.SocialProvider getSocialProvider(){
        return CommonEnums.SocialProvider.FACEBOOK;
    }

    public static class FacebookUser  {
    
        private String id;
        private String email;
        private String first_name;
        private String last_name;
        private Picture picture;
        @Data
        public static class Picture {
            private DataObject data;
        }
        @Data
        public static class DataObject {
            private String url;
        }    
    }

    @Override 
    public Map<String, String> authenticate(String token){
        if(Utils.isNullOrEmpty(token)){
            throw new AuthenticationException("Facebook auth Token is empty or null.");
        }
        try{
            String url =
                "https://graph.facebook.com/me"
                + "?fields=id,email,first_name,last_name,picture"
                + "&access_token=" + token;

            ResponseEntity<FacebookUser> response = restTemplate.getForEntity(url, FacebookUser.class);

            FacebookUser user = response.getBody();

            if (user == null) {
                throw new RuntimeException("Unable to fetch Facebook profile.");
            }

            Map<String, String> map = new HashMap<>();

            map.put("email", url);
            map.put("providerId", user.id);

            return map;

        }
        catch(Exception e){
            throw new AuthenticationException("Error while authenticating user.");
        }
    }
    
}
