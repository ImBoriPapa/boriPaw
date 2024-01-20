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
public class UserProfileService {

    private final UserAccountRepository userAccountRepository;
    private final UserProfileRepository userProfileRepository;

    @Transactional
    public UserAccountId addFollow() {

        return null;
    }

    @Transactional
    public UserAccountId addBlock() {

        return null;
    }

    @Transactional
    public UserAccountId processingUpdateNickname(UserAccountId userAccountId, String newNickname) {
        log.info("processing update profile nickname location: {}, new nickname: {}", userAccountId.getId(), newNickname);
        UserAccount userAccount = userAccountRepository.findById(userAccountId)
                .orElseThrow(() -> ResourceNotFoundException.forMessage("유저 계정 정보를 찾을 수 없습니다."));
        UserProfile userProfile = userProfileRepository.findByUserAccount(userAccount)
                .orElseThrow(() -> ResourceNotFoundException.forMessage("유저 프로필 정보를 찾을 수 없습니다."));
        UserProfile updatedUserProfile = userProfile.updateNickname(newNickname);
        userProfileRepository.update(updatedUserProfile);
        UserAccount updateNickname = userAccountRepository.update(updatedUserProfile.getUserAccount());
        return updateNickname.getUserAccountId();
    }

    @Transactional
    public UserAccountId processingUpdateIntroduce(UserAccountId userAccountId, String newIntroduce) {
        log.info("processing update profile introduce location: {}, new introduce: {}", userAccountId.getId(), newIntroduce);
        UserAccount userAccount = userAccountRepository.findById(userAccountId)
                .orElseThrow(() -> ResourceNotFoundException.forMessage("유저 계정 정보를 찾을 수 없습니다."));
        UserProfile userProfile = userProfileRepository.findByUserAccount(userAccount)
                .orElseThrow(() -> ResourceNotFoundException.forMessage("유저 프로필 정보를 찾을 수 없습니다."));
        UserProfile updateIntroduce = userProfile.updateIntroduce(newIntroduce);
        userProfileRepository.update(updateIntroduce);
        UserAccount updateNickname = userAccountRepository.update(updateIntroduce.getUserAccount());

        return updateNickname.getUserAccountId();
    }

    @Transactional
    public UserAccountId updateProfileImage() {
        return null;
    }
}
