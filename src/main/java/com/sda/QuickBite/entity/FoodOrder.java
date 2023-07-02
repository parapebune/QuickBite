package com.sda.QuickBite.entity;

import com.sda.QuickBite.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FoodOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private Double totalAmount;
    private LocalDateTime orderDate;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToOne
    @JoinColumn
    private OrderCart orderCart;

    @ManyToOne
    @JoinColumn
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn
    private User user;

}
