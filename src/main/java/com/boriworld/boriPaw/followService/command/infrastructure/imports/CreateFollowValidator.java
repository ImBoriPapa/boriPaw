package com.boriworld.boriPaw.followService.command.infrastructure.imports;

import com.boriworld.boriPaw.followService.command.domain.exception.AlreadyFollowException;
import com.boriworld.boriPaw.followService.command.domain.repository.FollowRepository;
import com.boriworld.boriPaw.followService.command.domain.service.FollowValidator;
import com.boriworld.boriPaw.followService.command.domain.usecase.FollowCreate;
import com.boriworld.boriPaw.userAccountService.command.domain.exception.BlockUserAccountException;
import com.boriworld.boriPaw.userAccountService.command.domain.useCase.UserAccountCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateFollowValidator implements FollowValidator {
    private final FollowRepository followRepository;

    @Override
    public boolean support(Class<?> clazz) {
        return FollowCreate.class.isAssignableFrom(clazz);
    }

    @Transactional(readOnly = true)
    @Override
    public void validate(Object o) {
        log.info("validate FollowCreate");
        FollowCreate followCreate = (FollowCreate) o;
        checkBlockFollower(followCreate);
        checkAlreadyFollow(followCreate);
    }

    private void checkAlreadyFollow(FollowCreate followCreate) {
        boolean exists = followRepository.existsByFollowerAndFollowing(followCreate.follower(), followCreate.following());
        if (exists) {
            //이미 팔로우 하고 있다면 예외
            throw AlreadyFollowException.forMessage("이미 팔로우 하고 있습니다.");
        }
    }

    private void checkBlockFollower(FollowCreate followCreate) {

        if (false) {
            throw BlockUserAccountException.forMessage("팔로우 할 수 없는 계정입니다.");
        }
    }
}
