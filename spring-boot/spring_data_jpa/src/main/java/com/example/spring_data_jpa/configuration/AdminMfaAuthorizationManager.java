package com.example.spring_data_jpa.configuration;

import org.springframework.security.core.Authentication;
import java.util.function.Supplier;
import org.springframework.lang.Nullable;
import org.springframework.security.authorization.AllAuthoritiesAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.authority.FactorGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class AdminMfaAuthorizationManager implements AuthorizationManager<Object> {

    private final AuthorizationManager<Object> mfa = AllAuthoritiesAuthorizationManager
            .hasAllAuthorities(FactorGrantedAuthority.OTT_AUTHORITY, FactorGrantedAuthority.PASSWORD_AUTHORITY);

    @Override
    public AuthorizationResult authorize(
        Supplier<? extends Authentication> authentication, Object context) {

        if ("admin".equals(authentication.get().getName())) {
            return this.mfa.authorize(authentication, context);
        } else {
            return new AuthorizationDecision(true);
        }
    }

}
