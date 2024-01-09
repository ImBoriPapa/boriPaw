package com.boriworld.boriPaw.followService.query.infrastructure.persistence;

import com.boriworld.boriPaw.followService.command.domain.value.FollowId;
import com.boriworld.boriPaw.followService.query.domain.model.*;
import com.boriworld.boriPaw.followService.query.domain.repository.FollowQueryRepository;

import com.boriworld.boriPaw.followService.query.domain.usecase.FollowersFindByCondition;
import com.boriworld.boriPaw.followService.query.domain.value.FollowerThatIFollow;
import com.boriworld.boriPaw.followService.query.domain.value.Requester;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.boriworld.boriPaw.followService.command.infrastructure.persistence.QFollowEntity.*;
import static com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence.QUserAccountEntity.*;

@Repository
@RequiredArgsConstructor
public class FollowQueryRepositoryJpaRepository implements FollowQueryRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * FollowersFindByCondition 를 이용해서 Followers 를 조회
     * Following 을 기준으로 Follow 의 Follower 를 조회
     * Following 이 없을 경우 Requester 를 기준으로 조회
     *
     * @param condition
     * @return
     */
    @Override
    public Followers findFollowersByCondition(FollowersFindByCondition condition) {
        Long followingId = condition.following() == null ? condition.requester().getUserAccountId().getId() : condition.following().getUserAccountId().getId();

        List<FollowerDetail> details = queryFactory
                .select(mapToFollowDetail())
                .from(followEntity)
                .leftJoin(userAccountEntity)
                .on(userAccountEntity.userAccountId.eq(followEntity.follower))
                .where(followEntity.following.eq(followingId)
                        .and(index(condition.followId())))
                .limit(condition.limit())
                .fetch();

        List<Long> followerIds = details.stream().map(d -> d.getFollowerProfile().getUserAccountId()).toList();

        List<FollowerThatIFollow> followersThatIFollow = findFollowerThatRequesterFollow(condition.requester(), followerIds);

        return Followers.of(condition, details, followersThatIFollow);
    }

    private List<FollowerThatIFollow> findFollowerThatRequesterFollow(Requester requester, List<Long> followerIds) {
        return queryFactory
                .select(followEntity.following)
                .from(followEntity)
                .where(followEntity.following
                        .in(followerIds)
                        .and(followEntity.follower.eq(requester.getUserAccountId().getId())))
                .fetch()
                .stream()
                .map(FollowerThatIFollow::of)
                .collect(Collectors.toList());
    }

    private ConstructorExpression<FollowerDetail> mapToFollowDetail() {
        return Projections.constructor(FollowerDetail.class,
                followEntity.followId.as("followId"),
                followEntity.follower.as("follower"),
                followEntity.following.as("following"),
                Expressions.constant(false),
                followEntity.followedAt.as("followedAt"),
                Projections.constructor(FollowerDetail.FollowerProfile.class,
                        userAccountEntity.userAccountId.as("userAccountId"),
                        userAccountEntity.userName.as("username"),
                        userAccountEntity.userProfileValue.nickname.as("nickname"),
                        userAccountEntity.userProfileValue.profileImage.as("profileImage")
                ).as("followerProfile")

        );
    }

    private BooleanExpression index(FollowId followId) {
        return followId == null ? null : Expressions.asNumber(followId.getId()).lt(followEntity.followId);
    }

}
