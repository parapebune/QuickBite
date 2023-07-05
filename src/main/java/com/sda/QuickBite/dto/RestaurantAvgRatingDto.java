package com.sda.QuickBite.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RestaurantAvgRatingDto {
    private String averageRatingDouble;
    private String averageRatingInt;
}
