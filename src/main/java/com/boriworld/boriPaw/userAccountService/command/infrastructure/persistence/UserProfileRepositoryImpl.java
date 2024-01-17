package com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence;

import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserProfileRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserProfileRepositoryImpl implements UserProfileRepository {

    private final UserProfileJpaRepository userProfileJpaRepository;

    @Override
    public UserProfile save(UserProfile userProfile) {
        return userProfileJpaRepository.save(UserProfileEntity.form(userProfile)).toModel();
    }


}
