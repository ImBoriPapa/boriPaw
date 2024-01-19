package com.boriworld.boriPaw.userAccountService.query.infrastructure.persistence;


import com.boriworld.boriPaw.followService.query.domain.value.Requester;
import com.boriworld.boriPaw.userAccountService.command.domain.value.RelationshipType;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence.RelationshipEntity;
import com.boriworld.boriPaw.userAccountService.query.domain.model.UserProfileDetail;
import com.boriworld.boriPaw.userAccountService.query.domain.repository.UserAccountQueryRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence.QRelationshipEntity.relationshipEntity;
import static com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence.QUserAccountEntity.*;
import static com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence.QUserProfileEntity.*;


@Repository
@RequiredArgsConstructor
@Slf4j
public class UserAccountQueryDslRepository implements UserAccountQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<UserProfileDetail> findUserProfileDetailByUserAccountId(Requester requester, UserAccountId userAccountId) {

        Optional<RelationshipEntity> optionalRelationship = jpaQueryFactory.select(relationshipEntity)
                .from(relationshipEntity)
                .where(relationshipEntity.subject.userAccountId.eq(requester.getUserAccountId().getId())
                        .and(relationshipEntity.target.userAccountId.eq(userAccountId.getId())))
                .fetch()
                .stream()
                .findFirst();

        boolean hasFollow = false;

        if (optionalRelationship.isEmpty()) {
            hasFollow = false;
        }

        if (optionalRelationship.isPresent()) {
            if (optionalRelationship.get().getType() == RelationshipType.BLOCK) {
                return Optional.empty();
            }

            if (optionalRelationship.get().getType() == RelationshipType.FOLLOW) {
                hasFollow = true;
            }
        }


        return jpaQueryFactory
                .select(Projections.constructor(UserProfileDetail.class,
                        userAccountEntity.userAccountId,
                        userAccountEntity.username,
                        userProfileEntity.nickname,
                        userProfileEntity.profileImageValue.fullPath,
                        userProfileEntity.introduce,
                        Expressions.constant(hasFollow),
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
