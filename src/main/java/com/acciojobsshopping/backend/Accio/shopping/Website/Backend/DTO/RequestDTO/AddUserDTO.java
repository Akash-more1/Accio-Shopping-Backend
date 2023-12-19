package com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.RequestDTO;

import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddUserDTO {

    String userName;

    String password;

    Long phoneNumber;

    String email;

    String address;

    Role role;
}
