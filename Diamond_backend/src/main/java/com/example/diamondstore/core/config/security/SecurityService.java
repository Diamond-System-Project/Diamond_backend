package com.example.diamondstore.core.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service("securityService")
public class SecurityService {
    public boolean hasAnyRoleId(Authentication authentication, Integer... roleIds) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            for (Integer roleId : roleIds) {
                if (authority.getAuthority().equals("ROLE_" + roleId)) {
                    return true;
                }
            }
        }
        return false;
    }
}
