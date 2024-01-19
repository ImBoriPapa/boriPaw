package com.boriworld.boriPaw.userAccountService.query.application;

import com.boriworld.boriPaw.followService.query.domain.value.Requester;

import com.boriworld.boriPaw.userAccountService.command.domain.model.Relationship;
import com.boriworld.boriPaw.userAccountService.command.domain.repository.RelationshipRepository;
import com.boriworld.boriPaw.userAccountService.command.domain.value.RelationshipSubject;
import com.boriworld.boriPaw.userAccountService.command.domain.value.RelationshipType;
import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;
import com.boriworld.boriPaw.userAccountService.query.domain.exception.ResourceNotFoundException;

import com.boriworld.boriPaw.userAccountService.query.domain.model.RelationshipInformation;
import com.boriworld.boriPaw.userAccountService.query.domain.model.UserProfileDetail;
import com.boriworld.boriPaw.userAccountService.query.domain.repository.RelationshipQueryRepository;
import com.boriworld.boriPaw.userAccountService.query.domain.repository.UserAccountQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAccountQueryService {
    private final UserAccountQueryRepository accountQueryRepository;


    public UserProfileDetail getUserDetail(Requester requester, UserAccountId userAccountId) {

        return accountQueryRepository.findUserProfileDetailByUserAccountId(requester,userAccountId)
                .orElseThrow(() -> ResourceNotFoundException.forMessage("프로필 정보를 찾을 수 없습니다"));
    }
}
