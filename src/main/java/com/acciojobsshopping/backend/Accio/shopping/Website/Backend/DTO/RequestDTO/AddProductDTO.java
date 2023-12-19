package com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.RequestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "dto used pass add product details to controllers")
public class AddProductDTO {

    String productName;

    String category;

    String description;

    String quantity;

    int price;

    String userName;
}
