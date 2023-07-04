package com.sda.QuickBite.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChangeForgottenPasswordDto {

    public String newPassword;
    private String newPasswordRetype;

}
