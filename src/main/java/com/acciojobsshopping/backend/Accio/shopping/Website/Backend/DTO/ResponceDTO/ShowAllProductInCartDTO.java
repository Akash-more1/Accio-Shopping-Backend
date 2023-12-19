package com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.ResponceDTO;

import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ShowAllProductInCartDTO {

    List<Product> products;

    int quantity;
    int totalPrice;

}
