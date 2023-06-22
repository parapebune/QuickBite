package com.sda.QuickBite.entity;

import jakarta.persistence.*;

@Entity



public class OrderEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;

    @ManyToOne
    @JoinColumn
    private Dish dish;



    @ManyToOne
    @JoinColumn
    private OrderCart orderCart;




}
