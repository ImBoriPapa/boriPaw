package com.boriworld.boriPaw.followService.command.application;

import com.boriworld.boriPaw.followService.command.domain.event.FollowCreateEvent;
import com.boriworld.boriPaw.followService.command.domain.event.FollowDeleteEvent;
import com.boriworld.boriPaw.followService.command.domain.model.*;
import com.boriworld.boriPaw.followService.command.domain.repository.FollowRepository;
import com.boriworld.boriPaw.followService.command.domain.event.FollowEventPublisher;
import com.boriworld.boriPaw.followService.command.domain.service.FollowValidator;
import com.boriworld.boriPaw.followService.command.domain.usecase.FollowCreate;
import com.boriworld.boriPaw.followService.command.domain.value.FollowId;
import com.boriworld.boriPaw.userAccountService.command.domain.exception.UnableToFindSupportedValidatorException;
import com.boriworld.boriPaw.userAccountService.query.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FollowService {
    private final FollowRepository followRepository;
    private final List<FollowValidator> followValidators;
    private final FollowEventPublisher eventPublisher;

    @Transactional
    public FollowId processFollow(FollowCreate followCreate) {
        log.info("process follow");
        validate(followCreate);
        log.info("here?");
        Follow follow = Follow.from(new FollowCreate(followCreate.follower(), followCreate.following()));

        Follow followed = followRepository.save(follow);

        eventPublisher.publish(new FollowCreateEvent(followed));

        return followed.getFollowId();
    }

    @Transactional
    public void processUnFollow(FollowId followId) {
        log.info("process unFollow");
        Follow follow = followRepository.findByFollowId(followId)
                .orElseThrow(() -> ResourceNotFoundException.forMessage("not found"));

        followRepository.delete(follow);

        eventPublisher.publish(new FollowDeleteEvent(follow));
    }

    private void validate(Object o) {
        followValidators.stream()
                .filter(validator -> validator.support(o.getClass()))
                .findFirst()
                .orElseThrow(() -> UnableToFindSupportedValidatorException.forMessage("검증기를 찾을 수 없습니다"))
                .validate(o);
    }
}
