package com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.RequestDTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogInRequestDTO {

    String userName;

    String password;
}
