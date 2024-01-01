package com.boriworld.boriPaw.followService.query.application;


import com.boriworld.boriPaw.followService.query.domain.FollowerQuery;
import com.boriworld.boriPaw.followService.query.domain.FollowerSlice;
import com.boriworld.boriPaw.followService.query.domain.FollowingQuery;
import com.boriworld.boriPaw.followService.query.domain.FollowingSlice;



public interface FollowQueryRepository {
    FollowerSlice findFollowersByFollowing(FollowerQuery followerQuery);
    FollowingSlice findFollowingsByFollower(FollowingQuery followingQuery);
}
