package com.boriworld.boriPaw.userAccountService.query.infrastructure.persistence;


import com.boriworld.boriPaw.followService.query.domain.value.Requester;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence.UserProfileEntity;
import com.boriworld.boriPaw.userAccountService.query.domain.model.UserProfileDetail;
import com.boriworld.boriPaw.userAccountService.query.domain.repository.UserAccountQueryRepository;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence.QUserAccountEntity.*;
import static com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence.QUserProfileEntity.*;


@Repository
@RequiredArgsConstructor
@Slf4j
public class UserAccountQueryDslRepository implements UserAccountQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<UserProfileDetail> findUserProfileDetailByUserAccountId(UserAccountId userAccountId) {
        return jpaQueryFactory
                .select(Projections.constructor(UserProfileDetail.class,
                        userAccountEntity.userAccountId,
                        userAccountEntity.userName,
                        userProfileEntity.nickname,
                        userProfileEntity.profileImageValue.fullPath,
                        userProfileEntity.introduce,
                        userProfileEntity.countOfPosts,
                        userProfileEntity.countOfFollowers,
                        userProfileEntity.countOfFollows
                ))
                .from(userAccountEntity)
                .leftJoin(userProfileEntity).on(userAccountEntity.userAccountId.eq(userProfileEntity.userAccountEntity.userAccountId))
                .where(userAccountEntity.userAccountId.eq(userAccountId.getId()))
                .fetch()
                .stream()
                .findFirst();

    }
}
