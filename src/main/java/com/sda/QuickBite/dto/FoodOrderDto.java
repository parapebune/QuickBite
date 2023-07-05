package com.sda.QuickBite.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FoodOrderDto {
    private String foodOrderId;
    private String date;
    private String restaurantName;
    private String restaurantId;
    private String totalAmount;
    private String status;
    private String rated;
}
