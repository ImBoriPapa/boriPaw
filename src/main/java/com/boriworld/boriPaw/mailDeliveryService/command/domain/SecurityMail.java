package com.boriworld.boriPaw.mailDeliveryService.command.domain;

import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public final class SecurityMail {
    private final MailId mailId;
    private final Sender sender;
    private final List<Recipient> receiver;
    private final String title;
    private final String content;
    private final MailType mailType;
    private final LocalDateTime mailingDateTime;

    @Builder(access = AccessLevel.PRIVATE)
    private SecurityMail(MailId mailId, Sender sender, List<Recipient> receiver, String title, String content, MailType mailType, LocalDateTime mailingDateTime) {
        this.mailId = mailId;
        this.sender = sender;
        this.receiver = receiver;
        this.title = title;
        this.content = content;
        this.mailType = mailType;
        this.mailingDateTime = mailingDateTime;
    }

    public static SecurityMail of() {
        return SecurityMail.builder()
                .build();
    }
}
