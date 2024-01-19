package com.boriworld.boriPaw.userAccountService.query.infrastructure.persistence;

import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence.QUserProfileEntity;
import com.boriworld.boriPaw.userAccountService.query.domain.model.UserInformation;
import com.boriworld.boriPaw.userAccountService.query.domain.repository.UserAuthenticationQueryRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence.QUserAccountEntity.userAccountEntity;
import static com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence.QUserProfileEntity.userProfileEntity;

@Repository
@RequiredArgsConstructor
public class UserAuthenticationQueryDslRepository implements UserAuthenticationQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<UserInformation> findUserInformationByUserAccountId(UserAccountId userAccountId) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(UserInformation.class,
                                userAccountEntity.userAccountId,
                                userAccountEntity.authority,
                                userAccountEntity.username,
                                userProfileEntity.profileImageValue.fullPath,
                                userAccountEntity.lastLoginAt
                        )
                )
                .from(userAccountEntity)
                .leftJoin(userProfileEntity)
                .on(userAccountEntity.userAccountId.eq(userProfileEntity.userAccountEntity.userAccountId))
                .where(userAccountEntity.userAccountId.eq(userAccountId.getId()))
                .fetch()
                .stream()
                .findFirst();
    }

}
