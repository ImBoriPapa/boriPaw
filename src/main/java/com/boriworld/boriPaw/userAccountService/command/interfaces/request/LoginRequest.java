package com.boriworld.boriPaw.userAccountService.command.interfaces.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank
    public String email;
    @NotBlank
    public String password;
}
