package com.boriworld.boriPaw.followService.query.domain.model;

import com.boriworld.boriPaw.followService.query.domain.value.FollowerThatIFollow;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class FollowerDetail {
    private Long followId;
    private boolean hasFollow;
    private LocalDateTime followedAt;
    private FollowerProfile followerProfile;

    public FollowerDetail(Long followId, boolean hasFollow, LocalDateTime followedAt, FollowerProfile followerProfile) {
        this.followId = followId;
        this.hasFollow = hasFollow;
        this.followedAt = followedAt;
        this.followerProfile = followerProfile;
    }

    public void setHasFollow(List<FollowerThatIFollow> followerThatIFollows) {
        this.hasFollow = followerThatIFollows.stream()
                .anyMatch(a -> a.getUserAccountId().getId().equals(this.followerProfile.userAccountId));
    }

    @Getter
    @NoArgsConstructor
    public static class FollowerProfile {
        private Long userAccountId;
        private String username;
        private String nickname;
        private String profileImage;

        public FollowerProfile(Long userAccountId, String username, String nickname, String profileImage) {
            this.userAccountId = userAccountId;
            this.username = username;
            this.nickname = nickname;
            this.profileImage = profileImage;
        }
    }


}
