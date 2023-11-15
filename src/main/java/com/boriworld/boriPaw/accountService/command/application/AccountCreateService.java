package com.boriworld.boriPaw.accountService.command.application;

import com.boriworld.boriPaw.accountService.command.domain.*;
import com.boriworld.boriPaw.accountService.command.domain.dto.AccountCreate;
import com.boriworld.boriPaw.accountService.command.domain.exception.AlreadyUsedAccountNameException;
import com.boriworld.boriPaw.accountService.command.domain.exception.AlreadyUsedEmailException;
import com.boriworld.boriPaw.accountService.command.domain.repository.AccountRepository;
import com.boriworld.boriPaw.accountService.command.domain.value.AccountId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountCreateService {

    private final AccountRepository accountRepository;
    private final AccountPasswordEncoder accountPasswordEncoder;
    private final AccountEventPublisher accountEventPublisher;
    private final EmailAuthenticationCodeGenerator emailAuthenticationCodeGenerator;
    private final EmailAuthenticationCodeRepository emailAuthenticationCodeRepository;

    @Transactional
    public AccountId createAccount(AccountCreate accountCreate) {

        checkDuplicateEmail(accountCreate.email());
        checkDuplicateAccountName(accountCreate.accountName());

        Account savedAccount = accountRepository.save(Account.from(accountCreate, accountPasswordEncoder));

        EmailAuthenticationCode authenticationCode = emailAuthenticationCodeRepository.save(EmailAuthenticationCode.create(savedAccount.getEmail(), emailAuthenticationCodeGenerator));

        publishCreateEvent(savedAccount, authenticationCode.getCode());

        return savedAccount.getAccountId();

    }

    private void publishCreateEvent(Account savedAccount, String authenticationCode) {
        accountEventPublisher.publish(new AccountCreateEvent(savedAccount.getAccountId(), savedAccount.getEmail(), authenticationCode));
    }

    private void checkDuplicateEmail(String email) {
        boolean exists = accountRepository.existsByEmail(email);

        if (exists) {
            throw new AlreadyUsedEmailException("이미 사용중인 이메일입니다.");
        }
    }

    private void checkDuplicateAccountName(String accountName) {

        boolean exists = accountRepository.existsByAccountName(accountName);

        if (exists) {
            throw new AlreadyUsedAccountNameException("이미 사용중인 계정명입니다.");
        }
    }
}
