package com.boriworld.boriPaw.userAccountService.command.domain.value;

import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import lombok.Getter;

@Getter
public final class RelationshipSubject {
    private final UserAccount userAccount;
    private RelationshipSubject(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public static RelationshipSubject of(UserAccount userAccount) {
        return new RelationshipSubject(userAccount);
    }

}
