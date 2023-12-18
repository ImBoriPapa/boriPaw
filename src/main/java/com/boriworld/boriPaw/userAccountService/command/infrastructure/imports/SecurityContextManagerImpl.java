package com.boriworld.boriPaw.userAccountService.command.infrastructure.imports;

import com.boriworld.boriPaw.userAccountService.command.domain.dto.UserAccountPrincipal;
import com.boriworld.boriPaw.userAccountService.command.domain.service.SecurityContextManager;
import com.boriworld.boriPaw.userAccountService.command.domain.value.Authority;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
public class SecurityContextManagerImpl implements SecurityContextManager {

    @Override
    public void setAuthentication(UserAccountId userAccountId, Authority authority) {
        SecurityContextHolder.getContext()
                .setAuthentication(UsernamePasswordAuthenticationToken
                        .authenticated(
                                createPrincipal(userAccountId, authority),
                                null,
                                getGrantedAuthorities(authority)));

    }

    private UserAccountPrincipal createPrincipal(UserAccountId userAccountId, Authority authority) {
        return new UserAccountPrincipal(userAccountId, authority);
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities(Authority authority) {
        return Arrays.stream(authority.getRoles())
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
