package com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int cid;

    @OneToOne
    Users user;

    @OneToMany
    List<Product>products;

    int totalItems;


    int totalPrice;

}
