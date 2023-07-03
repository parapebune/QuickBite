package com.sda.QuickBite.dto;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FullOrderDto {
    private FoodOrderDto foodOrderDto;
    private List<OrderCartEntryDto> orderCartEntryDtoList;
}
