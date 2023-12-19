package com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Controller;

import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.RequestDTO.AddProductInCartDTO;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.RequestDTO.AddUserDTO;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.RequestDTO.LogInRequestDTO;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.RequestDTO.PlaceOrderDTO;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.ResponceDTO.GeneralMessageDTO;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.ResponceDTO.LogInResponseDTO;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.ResponceDTO.OrderBillDTO;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.ResponceDTO.ShowAllProductInCartDTO;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Entity.Orders;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Entity.Users;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Exceptions.*;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Service.CartService;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Service.OrderService;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "User") //use to information and make bunch of end points under particular tag name

public class UsersController {

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;

    @Operation(   //operation used to give details to particular end point
            summary = "to add new user in application",
            description = "This end point used to add new user in application database",
            tags = { "User" }) //by specifying same tag end poin remain in same bunch

    @ApiResponses({  //to add in swagger document what response and its details this end point is give so frontend developer will know what exactly response he is getting
            @ApiResponse(responseCode = "201",description = "user added in database successfully", content = { @Content(schema = @Schema(implementation = Users.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", description = "when you are adding first person with role User", content = { @Content(schema = @Schema(implementation = String.class), mediaType = "application/json") })
    })
    @PostMapping("/api/signup")
    public ResponseEntity sinUp(@RequestBody AddUserDTO addUserDTO) {
       try {
            Users user = userService.signUp(addUserDTO);
            return new ResponseEntity(user, HttpStatus.CREATED);
        }
       catch (AddAdminFirstException e){
           return new ResponseEntity(e.getMessage(),HttpStatus.CONFLICT);
       }
    }

    @GetMapping("/api/login")
    public ResponseEntity logIn(@RequestBody LogInRequestDTO logInRequestDTO){

        try{
            LogInResponseDTO logInResponseDTO=userService.logIn(logInRequestDTO);
            return new ResponseEntity(logInResponseDTO,HttpStatus.FOUND);
        }
        catch (UserNotFoundException | WrongPasswordException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/api/approveAdmin")             //@Parameter is swagger annotation to make documentation
    public ResponseEntity approveAdmin(@Parameter(description = "existing Admin require to make user Admin",required = true ) @RequestParam String adminName,@Parameter(description = "Specify the user who what to became Admin",required = true ) @RequestParam int uid){
        try{
            userService.approveAdmin(adminName,uid);
            return new ResponseEntity(new GeneralMessageDTO("user with id "+uid+" became admin now"),HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/api/addProductInCart")
    public ResponseEntity addProductInCart(@RequestBody AddProductInCartDTO addProductInCartDTO){
        try{
            cartService.addProductInCart(addProductInCartDTO);
            return new ResponseEntity(new GeneralMessageDTO("product added in cart"),HttpStatus.CREATED);
        }
        catch (UserNotFoundException| ProductNotFoundException e){
            return  new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/showProductInCart/{uid}")
    public ResponseEntity showProductInCart(@PathVariable int uid){
        try{
            ShowAllProductInCartDTO showAllProductInCartDTO=cartService.showAllProductFromCart(uid);

            return new ResponseEntity(showAllProductInCartDTO,HttpStatus.OK);

        }
        catch (UserNotFoundException|ProductNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/deleteProductFromCart/{uid}/{pid}")
    public ResponseEntity deleteProductFromCart (@PathVariable int uid, @PathVariable int pid){
        try{
            cartService.deleteProductFromCart(uid,pid);
            return new ResponseEntity("product with pid "+pid+" deleted from database",HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/api/placeOrder")
    public  ResponseEntity placeOrder(@RequestBody PlaceOrderDTO placeOrderDTO){
        try{
          OrderBillDTO orderBillDTO= orderService.placeOrder(placeOrderDTO);
            return new ResponseEntity(orderBillDTO, HttpStatus.OK);
        }
        catch (UserNotFoundException| ProductNotFoundException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/findAllOrdersByUid/{uid}")
    public ResponseEntity findAllOrdersByUid(@PathVariable int uid){
        try{
            List<Orders> ordersList=orderService.findAllOrdersByUid(uid);
            return new ResponseEntity(ordersList,HttpStatus.OK);
        }
        catch (UserNotFoundException | NoOrderPacedYetException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/findOrdersByDeliveryPreference")
    public ResponseEntity findOrdersByDeliveryPreference(@RequestParam int uid, @RequestParam String isDelivered){
        try{
            List<Orders> list=orderService.findOrdersByDeliveryPreference(uid,isDelivered);
            return new ResponseEntity(list,HttpStatus.OK);
        }
        catch (UserNotFoundException e){
            return  new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (WrongDeliveryPreferenceException e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
