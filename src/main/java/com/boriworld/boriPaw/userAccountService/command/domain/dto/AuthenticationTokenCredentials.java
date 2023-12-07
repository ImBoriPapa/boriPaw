package com.boriworld.boriPaw.userAccountService.command.domain.dto;



import com.boriworld.boriPaw.userAccountService.command.domain.value.UserAccountId;

import java.util.Map;

public record AuthenticationTokenCredentials(UserAccountId userAccountId, Map<String, String> claims) { }
