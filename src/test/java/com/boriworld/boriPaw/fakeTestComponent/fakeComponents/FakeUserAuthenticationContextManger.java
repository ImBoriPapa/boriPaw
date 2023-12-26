package com.boriworld.boriPaw.fakeTestComponent.fakeComponents;

import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAuthenticationContextManager;
import com.boriworld.boriPaw.userAccountService.command.domain.value.Authority;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class FakeUserAuthenticationContextManger implements UserAuthenticationContextManager {
    private Authentication context;
    @Override
    public void setAuthentication(UserAccountId userAccountId, Authority authority) {
        String[] roles = authority.getRoles();
        Set<GrantedAuthority> authorities = new HashSet<>();
        Arrays.asList(roles)
                .forEach(string -> authorities.add(new SimpleGrantedAuthority(string)));
        context = new UsernamePasswordAuthenticationToken(userAccountId, authorities);
    }

    @Override
    public Authentication getAuthentication() {
        return context;
    }
}
