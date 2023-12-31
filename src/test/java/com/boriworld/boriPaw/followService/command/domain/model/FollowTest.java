package com.boriworld.boriPaw.followService.command.domain.model;


import com.boriworld.boriPaw.followService.command.domain.usecase.FollowCreate;
import com.boriworld.boriPaw.followService.command.domain.usecase.FollowInitialize;
import com.boriworld.boriPaw.followService.command.domain.value.FollowId;
import com.boriworld.boriPaw.followService.command.domain.value.Follower;
import com.boriworld.boriPaw.followService.command.domain.value.Following;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class FollowSmallTest {

    @Test
    @DisplayName("Follow Create 객체로 Follow 를 만들수 있다.")
    void givenFollowCreate_thenCreateFollow() throws Exception {
        //given
        Following following = Following.of(123L);
        Follower follower = Follower.of(321L);
        FollowCreate create = new FollowCreate(follower, following);
        //when
        Follow follow = Follow.from(create);
        //then
        assertAll(() -> {
            assertThat(follow).isNotNull();
            assertThat(follow.getFollowId()).isNull();
        });

    }

    @Test
    @DisplayName("FollowInitialize 객체로 Follow 를 초기화 할수 있다.")
    void givenFollowInitialize_thenInitializeFollow() throws Exception {
        //given
        FollowId followId = FollowId.of(123L);
        Following following = Following.of(1234L);
        Follower follower = Follower.of(12345L);
        LocalDateTime followedAt = LocalDateTime.now();
        FollowInitialize initialize = new FollowInitialize(followId, following, follower, followedAt);
        //when
        Follow follow = Follow.initialize(initialize);
        //then
        assertAll(() -> {
            assertThat(follow).isNotNull();
        });
    }

    @Test
    @DisplayName("같은 FollowId 로 Follow 객체의 동일성을 보장한다.")
    void givenSameFollowId_thenIsSameFollow() throws Exception {
        //given
        FollowId followId = FollowId.of(123L);
        Following following = Following.of(1234L);
        Follower follower = Follower.of(12345L);
        LocalDateTime followedAt = LocalDateTime.now();
        FollowInitialize initialize = new FollowInitialize(followId, following, follower, followedAt);
        //when
        Follow follow1 = Follow.initialize(initialize);
        Follow follow2 = Follow.initialize(initialize);
        //then
        assertThat(follow1).isEqualTo(follow2);

    }

}