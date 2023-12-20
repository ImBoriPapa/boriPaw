package com.boriworld.boriPaw.userAccountService.query.infrastructure.persistence;


import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import com.boriworld.boriPaw.userAccountService.query.domain.model.UserInformation;
import com.boriworld.boriPaw.userAccountService.query.domain.repository.UserAccountQueryRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence.QUserAccountEntity.*;


@Repository
@RequiredArgsConstructor
public class QueryDslUserAccountQueryRepository implements UserAccountQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<UserInformation> findUserInformationByAccountId(UserAccountId userAccountId) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(UserInformation.class,
                                userAccountEntity.userAccountId,
                                userAccountEntity.authority,
                                userAccountEntity.lastLoginAt
                        )
                )
                .from(userAccountEntity)
                .where(userAccountEntity.userAccountId.eq(userAccountId.getId()))
                .fetch()
                .stream()
                .findFirst();
    }
}
