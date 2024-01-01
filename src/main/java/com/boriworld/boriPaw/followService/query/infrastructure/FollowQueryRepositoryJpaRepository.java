package com.boriworld.boriPaw.followService.query.infrastructure;

import com.boriworld.boriPaw.followService.command.domain.model.Follow;
import com.boriworld.boriPaw.followService.command.domain.value.Follower;
import com.boriworld.boriPaw.followService.command.domain.value.Following;

import com.boriworld.boriPaw.followService.query.application.FollowQueryRepository;

import com.boriworld.boriPaw.followService.query.domain.*;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.boriworld.boriPaw.followService.command.infrastructure.persistence.QFollowEntity.*;
import static com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence.QUserAccountEntity.*;

@Repository
@RequiredArgsConstructor
public class FollowQueryRepositoryJpaRepository implements FollowQueryRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public FollowerSlice findFollowersByFollowing(FollowerQuery query) {

        final int SIZE = 10;
        final int LIMIT = SIZE + 1;
        boolean hasNext = false;
        long last = query.getLast();

        List<FollowerDetail> followerDetails = queryFactory
                .select(
                        Projections.constructor(FollowerDetail.class,
                                followEntity.followId.as("followId"),
                                userAccountEntity.userAccountId.as("userAccountId"),
                                userAccountEntity.userName.as("username"),
                                userAccountEntity.userProfileValue.nickname.as("nickname"),
                                userAccountEntity.userProfileValue.profileImage.as("profileImage")
                        )
                )
                .from(followEntity)
                .leftJoin(userAccountEntity)
                .on(userAccountEntity.userAccountId.eq(followEntity.follower))
                .where(followEntity.following.eq(query.getFollowing().getUserAccountId().getId())
                        .and(index(last))
                )
                .limit(LIMIT)
                .fetch();

        hasNext = isHasNext(SIZE, LIMIT, hasNext, followerDetails);

        last = followerDetails.stream()
                .mapToLong(FollowerDetail::getFollowId)
                .max()
                .orElse(last);

        return new FollowerSlice(last, hasNext, followerDetails);
    }

    @Override
    public FollowingSlice findFollowingsByFollower(FollowingQuery query) {

        final int SIZE = 10;
        final int LIMIT = SIZE + 1;
        boolean hasNext = false;
        long last = query.getLast();

        List<FollowingDetail> fetch = queryFactory
                .select(
                        Projections.constructor(FollowingDetail.class)
                )
                .from(followEntity)
                .leftJoin(userAccountEntity)
                .on(userAccountEntity.userAccountId.eq(followEntity.following))
                .where(followEntity.follower.eq(query.getFollowing().getUserAccountId().getId())
                        .and(index(query.getLast()))
                )
                .limit(10L)
                .fetch();

        boolean next = isHasNext(SIZE, LIMIT, hasNext, fetch);

        last = fetch.stream()
                .mapToLong(FollowingDetail::getFollowId)
                .max()
                .orElse(last);

        return new FollowingSlice(last, next, fetch);
    }

    private boolean isHasNext(int SIZE, int LIMIT, boolean hasNext, List<?> objects) {
        if (objects.size() == LIMIT) {
            objects.remove(SIZE);
            hasNext = true;
        }
        return hasNext;
    }

    private BooleanExpression index(Long last) {
        return last == null ? null : Expressions.asNumber(last).lt(followEntity.followId);
    }
}
