package com.boriworld.boriPaw.userAccountService.command.domain.value;

import lombok.Getter;

@Getter
public final class UserProfileId {
    Long id;
    private UserProfileId(Long id) {
        this.id = id;
    }
    public static UserProfileId of(Long id) {
        return new UserProfileId(id);
    }

}
