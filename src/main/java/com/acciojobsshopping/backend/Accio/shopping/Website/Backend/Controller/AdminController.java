package com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Controller;

import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.RequestDTO.AddProductDTO;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.ResponceDTO.GeneralMessageDTO;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Entity.Product;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Exceptions.OrderNotFountException;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Exceptions.UserNotFoundException;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Exceptions.WrongAccessException;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Service.OrderService;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Admin APIs")
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;


    @PostMapping("/addProduct")
    public ResponseEntity addProduct(@RequestBody AddProductDTO addProductDTO){
        try{
            productService.addProduct(addProductDTO);
            return new ResponseEntity(new GeneralMessageDTO("product added successfully"), HttpStatus.CREATED);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        catch ( WrongAccessException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/findAllProducts/{userName}")
    public  ResponseEntity findAllProducts(@PathVariable String userName){
        try {
            List<Product> list=productService.findAllProduct(userName);
            return new ResponseEntity(list,HttpStatus.FOUND);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        catch (WrongAccessException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/cancelOrder")
    public ResponseEntity cancelOrder(@RequestParam int oid, @RequestParam int uid){
        try{
            orderService.cancelOrder(oid,uid);
            return  new ResponseEntity(String.format("order with Oid %d got canceled",oid),HttpStatus.OK);
        }
        catch (UserNotFoundException| OrderNotFountException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (WrongAccessException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
    }
}
