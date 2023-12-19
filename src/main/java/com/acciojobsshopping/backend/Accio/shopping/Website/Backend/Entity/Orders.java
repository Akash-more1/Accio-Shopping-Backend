package com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    int orderName;

    @ManyToOne
    Users user;

    Date estimatedDelivery;

    int totalOrderPrice;

    @ManyToMany
    List<Product> orderItems;

    boolean isDelivered;

    int totalOrderItems;
}
