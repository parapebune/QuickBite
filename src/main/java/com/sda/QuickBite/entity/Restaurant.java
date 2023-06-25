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
@ToString
@Getter
@Setter
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phoneNo;
    private Double rating;

    @Enumerated(value = EnumType.STRING)
    private RestaurantSpecific restaurantSpecific;
    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] logo;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Dish> menu = new ArrayList<>();
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn
//    private RestaurantMenu restaurantMenu;

}
