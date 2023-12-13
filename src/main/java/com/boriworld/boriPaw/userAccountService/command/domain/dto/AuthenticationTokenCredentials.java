package com.boriworld.boriPaw.userAccountService.command.domain.dto;


import java.util.Map;

public record AuthenticationTokenCredentials(String subject, Map<String, String> claims) { }
