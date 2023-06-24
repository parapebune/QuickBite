package com.sda.QuickBite.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String role;

}
