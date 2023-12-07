package com.boriworld.boriPaw.userAccountService.command.domain.value;

import java.util.Arrays;

/**
 * Value Object
 */
public enum Authority {
    GUEST(new String[]{"GUEST"}),
    USER(new String[]{"GUEST", "USER"}),
    ADMIN(new String[]{"GUEST", "USER", "ADMIN"});
    private final String[] role;

    Authority(String[] role) {
        this.role = role;
    }

    public static Authority formString(String string) {
        return Arrays.stream(Authority.values())
                .filter(authority -> authority.name().equals(string))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없다."));
    }

    public String[] getRoles() {
        return this.role;
    }

}
