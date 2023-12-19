package com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlaceOrderDTO {

    int uid;
    List<Integer> list;


}
