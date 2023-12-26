package com.boriworld.boriPaw.userAccountService.query.application;

import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import com.boriworld.boriPaw.userAccountService.query.domain.exception.ResourceNotFoundException;

import com.boriworld.boriPaw.userAccountService.query.domain.model.UserProfileDetail;
import com.boriworld.boriPaw.userAccountService.query.domain.repository.UserAccountQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class UserAccountQueryService {
    private final UserAccountQueryRepository repository;

    public UserProfileDetail getUserDetail(UserAccountId userAccountId) {

        return repository.findUserProfileDetailByUserAccountId(userAccountId)
                .orElseThrow(() -> ResourceNotFoundException.forMessage("프로필 정보를 찾을 수 없습니다"));
    }
}
