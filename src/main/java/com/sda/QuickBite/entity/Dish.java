package com.sda.QuickBite.entity;

import jakarta.persistence.*;

@Entity
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double price;
    private Integer cookingTime;

    private Double rating;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] logo;

    @ManyToOne
    @JoinColumn(name = "restaurantId")
    private Restaurant restaurant;


}

