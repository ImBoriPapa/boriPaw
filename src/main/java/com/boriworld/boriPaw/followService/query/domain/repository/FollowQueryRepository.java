package com.boriworld.boriPaw.followService.query.domain.repository;


import com.boriworld.boriPaw.followService.query.domain.model.Followers;

import com.boriworld.boriPaw.followService.query.domain.usecase.FollowersFindByCondition;


public interface FollowQueryRepository {


    Followers findFollowersByCondition(FollowersFindByCondition condition);
}
