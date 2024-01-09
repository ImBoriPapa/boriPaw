package com.boriworld.boriPaw.followService.query.domain.model;

import com.boriworld.boriPaw.followService.command.infrastructure.persistence.FollowEntity;
import com.boriworld.boriPaw.followService.query.domain.usecase.FollowersFindByCondition;
import com.boriworld.boriPaw.followService.query.domain.value.FollowerThatIFollow;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
public class Followers implements NoOffSetSlice {
    private Long last;
    private Boolean hasNext;
    private List<FollowerDetail> followers;

    public Followers(Long last, Boolean hasNext, List<FollowerDetail> followers) {
        this.last = last;
        this.hasNext = hasNext;
        this.followers = followers;
    }

    public static Followers of(FollowersFindByCondition condition, List<FollowerDetail> details, List<FollowerThatIFollow> followerThatIFollows) {
        Followers followers = new Followers(0L, false, details);
        followers.calculateLastIndex();
        followers.calculateHasNext(condition.limit());
        followers.followersSet(followerThatIFollows);
        return followers;
    }

    private void followersSet(List<FollowerThatIFollow> followerThatIFollows) {
        if (!this.followers.isEmpty()) {
            this.followers.forEach(d -> d.setHasFollow(followerThatIFollows));
        }
    }

    private void calculateLastIndex() {
        this.last = this.followers.stream()
                .mapToLong(FollowerDetail::getFollowId)
                .max()
                .orElse(last);
    }

    private void calculateHasNext(Integer size) {
        int defaultLimit = 11;
        int limit = size == null ? defaultLimit : size + 1;
        boolean next = false;

        if (this.followers.size() == limit) {
            this.followers.remove(limit - 1);
            next = true;
        }
        this.hasNext = next;
    }
}
