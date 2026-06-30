package com.yatrago.backend.auth.providers;

import com.yatrago.backend.common.CommonEnums.SocialProvider;

import java.util.Map;

public interface SocialAuthProvider {

    public SocialProvider getSocialProvider();

    Map<String, String> authenticate(String token);
    
}
