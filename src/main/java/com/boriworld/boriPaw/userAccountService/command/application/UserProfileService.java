package com.boriworld.boriPaw.userAccountService.command.application;

import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserAccountRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserProfileRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserProfile;
import com.boriworld.boriPaw.userAccountService.query.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserProfileService {

    private final UserAccountRepository userAccountRepository;
    private final UserProfileRepository userProfileRepository;

    public UserAccountId addFollow() {

        return null;
    }

    public UserAccountId addBlock() {

        return null;
    }

    public UserAccountId updateNickname(UserAccountId userAccountId, String newNickname) {

        UserAccount userAccount = userAccountRepository.findById(userAccountId)
                .orElseThrow(() -> ResourceNotFoundException.forMessage("유저 계정 정보를 찾을 수 없습니다."));
        UserProfile userProfile = userProfileRepository.findByUserAccount(userAccount)
                .orElseThrow(() -> ResourceNotFoundException.forMessage("유저 프로필 정보를 찾을 수 없습니다."));
        UserProfile updatedUserProfile = userProfile.updateNickname(newNickname);
        userProfileRepository.update(updatedUserProfile);
        UserAccount update = userAccountRepository.update(updatedUserProfile.getUserAccount());
        return update.getUserAccountId();
    }

    public UserAccountId updateIntroduce() {
        return null;
    }

    public UserAccountId updateProfileImage() {
        return null;
    }
}
