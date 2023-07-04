package com.sda.QuickBite.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FeedbackDto {
    private String id;
    private String userFullName;
    private String rating;
    private String review;
}
