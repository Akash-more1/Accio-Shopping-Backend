package com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int uid;

    @Column(unique = true)
    String userName;

    String password;

    String role;

    String address;

    @Column(unique = true)
    String emailAddress;

    @Column(unique = true, length = 10)
    Long phoneNumber;

    boolean isAdminApprove;


}
