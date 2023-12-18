package com.boriworld.boriPaw.userAccountService.query.repository;


import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;

import com.boriworld.boriPaw.userAccountService.query.response.UserAccountMe;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence.QUserAccountEntity.*;


@Repository
@RequiredArgsConstructor
public class UserAccountQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Optional<UserAccountMe> get(Long userAccountId) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(UserAccountMe.class,
                                userAccountEntity.userAccountId,
                                userAccountEntity.authority,
                                userAccountEntity.lastLoginAt
                        )
                )
                .from(userAccountEntity)
                .where(userAccountEntity.userAccountId.eq(userAccountId))
                .fetch()
                .stream()
                .findFirst();
    }
}
