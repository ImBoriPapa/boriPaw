package com.boriworld.boriPaw.followService.command;

import com.boriworld.boriPaw.followService.command.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final FollowerRepository followerRepository;
    private final UserInformationRepository userInformationRepository;

    @Transactional
    public void registerUserInformation(FollowUserCreate followUserCreate) {
        UserInformation userInformation = UserInformation.of(followUserCreate);
        userInformationRepository.save(userInformation);
    }

    @Transactional
    public void doFollow(AccountUserId followedId, AccountUserId followerId) {

        UserInformation followedInformation = userInformationRepository.findByAccountUserId(followedId)
                .orElseThrow();

        UserInformation followerInformation = userInformationRepository.findByAccountUserId(followerId)
                .orElseThrow();

        Follower follower = followerRepository.save(Follower.of(followerInformation));

        Follow follow = Follow.of(null);
        followRepository.save(follow);
    }
}
