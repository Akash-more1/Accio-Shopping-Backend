package com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddProductInCartDTO {

    int uid;

    int pid;

    int quantity;
}
