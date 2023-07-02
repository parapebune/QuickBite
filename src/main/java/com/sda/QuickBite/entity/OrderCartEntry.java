package com.sda.QuickBite.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter


public class OrderCartEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderCartEntryId;
    private Integer quantity;

    @ManyToOne
    @JoinColumn
    private Dish dish;

    @ManyToOne
    @JoinColumn
    private OrderCart orderCart;




}
