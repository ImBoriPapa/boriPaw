package com.boriworld.boriPaw.userAccountService.query.application;

import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import com.boriworld.boriPaw.userAccountService.query.domain.exception.ResourceNotFoundException;
import com.boriworld.boriPaw.userAccountService.query.domain.model.UserInformation;
import com.boriworld.boriPaw.userAccountService.query.domain.repository.UserAuthenticationQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserAuthenticationQueryService {

    private final UserAuthenticationQueryRepository queryRepository;

    public UserInformation getUserInformationBy(UserAccountId userAccountId) {

        return queryRepository.findUserInformationByUserAccountId(userAccountId)
                .orElseThrow(() -> ResourceNotFoundException.forMessage("사용자 정보를 찾을 수 없습니다."));

    }
}
