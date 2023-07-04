package com.sda.QuickBite.dto;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SummaryOrderCartDto {
    private String restaurantName;

    private String totalAmount;
    private List<OrderCartEntryDto> orderCartEntryDtoList;

}
