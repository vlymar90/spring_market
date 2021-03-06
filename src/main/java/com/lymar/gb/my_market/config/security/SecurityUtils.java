package com.lymar.gb.my_market.config.security;

import com.vaadin.flow.server.HandlerHelper;
import com.vaadin.flow.shared.ApplicationConstants;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

public class SecurityUtils {
    enum Roles {
        USER,
        ADMIN,
        MANAGER;
    }

    public static boolean isFrameworkInternalRequest(HttpServletRequest request) {
        final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return parameterValue != null
                && Stream.of(HandlerHelper.RequestType.values())
                .anyMatch(r -> r.getIdentifier().equals(parameterValue));
    }

    public static boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(Roles.ADMIN.name()));
    }

    public static boolean isManager(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(Roles.MANAGER.name()));
    }

}
