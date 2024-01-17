package com.boriworld.boriPaw;

import com.boriworld.boriPaw.userAccountService.command.domain.service.UserAccountPasswordEncoder;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Profile("local")
@Component
@RequiredArgsConstructor
@Transactional
public class TestData {
    private final UserAccountPasswordEncoder userAccountPasswordEncoder;
    private final EntityManager em;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

    }
}
