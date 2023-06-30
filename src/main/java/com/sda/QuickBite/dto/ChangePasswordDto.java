package com.sda.QuickBite.dto;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter


public class ChangePasswordDto {


    @NotEmpty(message = "Password is required")
    private String oldPassword;

    @NotEmpty(message = "Password is required")
    private String newPassword;

    @NotEmpty(message = "Retype New Password")
    private String newPasswordRetype;


}
