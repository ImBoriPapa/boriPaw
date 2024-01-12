package com.boriworld.boriPaw;

import com.boriworld.boriPaw.followService.command.domain.model.Follow;
import com.boriworld.boriPaw.followService.command.domain.usecase.FollowCreate;
import com.boriworld.boriPaw.followService.command.domain.value.Follower;
import com.boriworld.boriPaw.followService.command.domain.value.Following;
import com.boriworld.boriPaw.followService.command.infrastructure.persistence.FollowEntity;
import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserAccountRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAccountPasswordEncoder;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserAccountCreate;
import com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence.UserAccountEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
@Transactional
public class TestData {
    private final UserAccountPasswordEncoder userAccountPasswordEncoder;
    private final EntityManager em;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        List<UserAccount> users = new ArrayList();
        IntStream.rangeClosed(1, 30).forEach(index -> {
            String email = "email" + index + "@gmail.com";
            String password = "testpassword1234";
            String nickname = "nickname" + index;
            UserAccountCreate userAccountCreate = new UserAccountCreate(
                    email, password, nickname
            );
            users.add(UserAccount.from(userAccountCreate, userAccountPasswordEncoder));
        });
        users.stream()
                .map(UserAccountEntity::fromModel)
                .forEach(em::persist);
        em.flush();
        em.clear();


        Following following = Following.of(1L);
        Follower follower2 = Follower.of(2L);
        Follower follower3 = Follower.of(3L);
        Follower follower4 = Follower.of(4L);
        Follower follower5 = Follower.of(5L);
        Follower follower6 = Follower.of(6L);
        Follower follower7 = Follower.of(7L);

        List<Follower> followers = List.of(follower2, follower3, follower4, follower5, follower6, follower7);
        List<Follow> follows = new ArrayList<>();
        followers
                .forEach(follower -> {
                    Follow follow = Follow.from(new FollowCreate(follower, following));
                    follows.add(follow);
                });

        follows.stream()
                .map(FollowEntity::fromModel)
                .forEach(em::persist);
    }

}
