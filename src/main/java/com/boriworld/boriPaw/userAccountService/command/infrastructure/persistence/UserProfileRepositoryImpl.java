package com.boriworld.boriPaw.userAccountService.command.infrastructure.persistence;

import com.boriworld.boriPaw.userAccountService.command.domain.model.UserAccount;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.UserProfileRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserProfileRepositoryImpl implements UserProfileRepository {

    private final UserProfileJpaRepository userProfileJpaRepository;

    @Override
    public UserProfile save(UserProfile userProfile) {
        return userProfileJpaRepository.save(UserProfileEntity.form(userProfile)).toModel();
    }

    @Override
    public Optional<UserProfile> findByUserAccount(UserAccount userAccount) {
        return userProfileJpaRepository.findByUserAccountEntity(UserAccountEntity.fromModel(userAccount))
                .map(UserProfileEntity::toModel);
    }

    @Override
    public void update(UserProfile userProfile) {
        userProfileJpaRepository.saveAndFlush(UserProfileEntity.form(userProfile));
    }



}
