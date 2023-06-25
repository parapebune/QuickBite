package com.sda.QuickBite.entity;

import com.sda.QuickBite.enums.DishCategory;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
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
    private DishCategory category;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;


}

