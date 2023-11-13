package com.boriworld.boriPaw.accountService.command.application;

import com.boriworld.boriPaw.accountService.command.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountCreateService {

    private final AccountRepository accountRepository;
    private final AccountPasswordEncoder accountPasswordEncoder;

    @Transactional
    public AccountId createAccount(final String email) {

        boolean isExists = accountRepository.existsByEmail(email);

        if (isExists) {
            throw new AlreadyUsedEmailException("이미 사용중인 이메일 입니다.");
        }



        Account savedAccount = accountRepository.save(Account.createAccount());

        return savedAccount.getAccountId();

    }
}
