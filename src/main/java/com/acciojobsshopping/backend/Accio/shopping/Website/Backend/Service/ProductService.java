package com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Service;

import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.RequestDTO.AddProductDTO;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.ResponceDTO.GeneralMessageDTO;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Entity.Product;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Exceptions.WrongAccessException;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

@Autowired
    ProductRepository productRepository;

@Autowired
UserService userService;

public void addProduct(AddProductDTO addProductDTO){
     if(userService.isAdmin(addProductDTO.getUserName())){
         Product product=new Product();
         product.setProductName(addProductDTO.getProductName());
         product.setCategory(addProductDTO.getCategory());
         product.setDescription(addProductDTO.getDescription());
         product.setPrice(addProductDTO.getPrice());
         product.setQuantity(addProductDTO.getQuantity());

         productRepository.save(product);

     }
     else{
       throw  new WrongAccessException("user does not have admin access");
     }
}

public List<Product> findAllProduct( String username){
     Boolean b=userService.isAdmin(username);
     if(b==false){
         throw  new WrongAccessException("user is not admin so he cant see details");
     }
     else{
         return productRepository.findAll();
     }
}

public Product findById(int pid){
   return  productRepository.findById(pid).orElse(null);
}
}
