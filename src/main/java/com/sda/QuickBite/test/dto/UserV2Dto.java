package com.sda.QuickBite.test.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserV2Dto {
    private String name;
    private String email;
    private String password;
}
