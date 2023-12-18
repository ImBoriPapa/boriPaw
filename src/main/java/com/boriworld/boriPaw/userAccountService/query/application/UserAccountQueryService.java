package com.boriworld.boriPaw.userAccountService.query.application;

import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import com.boriworld.boriPaw.userAccountService.query.domain.model.UserInformation;
import com.boriworld.boriPaw.userAccountService.query.domain.repository.UserAccountQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAccountQueryService {
    private final UserAccountQueryRepository repository;
    public UserInformation getUserInformationBy(UserAccountId userAccountId) {

        Optional<UserInformation> userAccountMe = repository.findUserInformationByAccountId(userAccountId);

        return userAccountMe.orElseThrow(() -> ResourceNotFoundException.forMessage("유저 정보를 찾을 수 없습니다."));
    }
}
