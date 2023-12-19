package com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Service;

import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.RequestDTO.AddProductInCartDTO;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.ResponceDTO.ShowAllProductInCartDTO;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Entity.Cart;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Entity.Product;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Entity.Users;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Exceptions.ProductNotFoundException;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Exceptions.UserNotFoundException;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;
    public void createCart(int uid){
        Cart cart=new Cart();

        Users user=userService.findById(uid);
        if(user==null){
            throw new UserNotFoundException(String.format("user with uid %d not in database",uid));
        }
        cart.setUser(user);
        cartRepository.save(cart);
    }

    public void  addProductInCart(AddProductInCartDTO addProductInCartDTO){
          Product product= productService.findById(addProductInCartDTO.getPid());
          if(product==null){
              throw new ProductNotFoundException("product not found");
          }
          List<Object[]> list=cartRepository.findCIByUid(addProductInCartDTO.getUid());

        if(list.size()==0){
            createCart(addProductInCartDTO.getUid());
        }

        int cartId=Integer.parseInt(cartRepository.findCIByUid(addProductInCartDTO.getUid()).get(0)[0].toString());
        Cart cart=cartRepository.findById(cartId).orElse(null);



        int totalQuantity=cart.getTotalItems()+ addProductInCartDTO.getQuantity();
        int totalPrice=cart.getTotalPrice()+ (addProductInCartDTO.getQuantity()*product.getPrice());

        cart.setTotalPrice(totalPrice);
        cart.setTotalItems(totalQuantity);

        cartRepository.updateQuantityAnsPriceOfCart(totalQuantity,totalPrice,cartId);
        cartRepository.insertCiVsPid(cartId,addProductInCartDTO.getPid());



    }

    public ShowAllProductInCartDTO showAllProductFromCart(int uid){

        if(userService.findById(uid)==null){
            throw new  UserNotFoundException("user is not in database");
        }
        Users user=userService.findById(uid);

        int cartId=Integer.parseInt(cartRepository.findCIByUid(user.getUid()).get(0)[0].toString());
        Cart cart=cartRepository.findById(cartId).orElse(null);

        if(cart==null){
            throw new ProductNotFoundException("user not added any product in his cart");
        }

        List<Object[]> list=cartRepository.findAllProductsInCart(cartId);
        List<Product> productList=new ArrayList<>();

        for(Object[] ele: list){
            Product product=productService.findById(Integer.parseInt(ele[0].toString()));
            productList.add(product);
        }

        ShowAllProductInCartDTO showAllProductInCartDTO=new ShowAllProductInCartDTO();
        showAllProductInCartDTO.setProducts(productList);
        showAllProductInCartDTO.setQuantity(cart.getTotalItems());
        showAllProductInCartDTO.setTotalPrice(cart.getTotalPrice());

        return showAllProductInCartDTO;
    }

    public void deleteProductFromCart (int uid, int pid){
        if(userService.findById(uid)==null){
            throw new UserNotFoundException("user not present in database");
        }

        Users user= userService.findById(uid);

        Product product=productService.findById(pid);

        int cartId=Integer.parseInt(cartRepository.findCIByUid(uid).get(0)[0].toString());;

        Cart cart=cartRepository.findById(cartId).orElse(null);

        int newPrice=cart.getTotalPrice()-product.getPrice();
        int newItem=cart.getTotalItems()-1;
        cartRepository.updateQuantityAnsPriceOfCart(newItem,newPrice, cartId);
        cartRepository.deleteProductFromCart(pid);

    }
}
