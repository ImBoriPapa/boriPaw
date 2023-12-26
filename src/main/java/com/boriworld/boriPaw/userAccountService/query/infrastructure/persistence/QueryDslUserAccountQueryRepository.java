package com.boriworld.boriPaw.userAccountService.query.infrastructure.persistence;


import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import com.boriworld.boriPaw.userAccountService.query.domain.model.UserInformation;
import com.boriworld.boriPaw.userAccountService.query.domain.model.UserProfileDetail;
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
    public Optional<UserProfileDetail> findUserProfileDetailByUserAccountId(UserAccountId userAccountId) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(UserProfileDetail.class,
                                userAccountEntity.userAccountId,
                                userAccountEntity.userName,
                                userAccountEntity.userProfileValue.nickname,
                                userAccountEntity.userProfileValue.profileImage,
                                userAccountEntity.userProfileValue.introduce
                        )
                )
                .from(userAccountEntity)
                .where(userAccountEntity.userAccountId.eq(userAccountId.getId()))
                .fetch()
                .stream()
                .findFirst();
    }
}
