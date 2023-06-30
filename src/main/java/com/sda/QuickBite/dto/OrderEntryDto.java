package com.sda.QuickBite.dto;

import com.sda.QuickBite.entity.Dish;
import com.sda.QuickBite.entity.OrderCart;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderEntryDto {


    private String id;
    private String quantity;
    private String dish;
    private String orderCart;
}
