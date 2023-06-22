package com.sda.QuickBite.entity;

import com.sda.QuickBite.enums.RestaurantSpecific;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phoneNumber;
    private String email;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private RestaurantSpecific restaurantSpecific;
    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] logo;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dish> menu = new ArrayList<>();
    private Double rating;


}
