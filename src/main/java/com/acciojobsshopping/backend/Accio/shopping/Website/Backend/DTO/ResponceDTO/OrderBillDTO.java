package com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.ResponceDTO;

import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderBillDTO {

    List<Product> products;

    int totalPrice;
}
