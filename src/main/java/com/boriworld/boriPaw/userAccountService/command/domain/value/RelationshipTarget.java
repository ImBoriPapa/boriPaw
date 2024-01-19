package com.boriworld.boriPaw.userAccountService.command.domain.value;

import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import lombok.Getter;


@Getter
public final class RelationshipTarget {
    private final UserAccount userAccount;

    private RelationshipTarget(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public static RelationshipTarget of(UserAccount userAccount) {
        return new RelationshipTarget(userAccount);
    }

}
