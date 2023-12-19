package com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Service;

import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.RequestDTO.PlaceOrderDTO;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.DTO.ResponceDTO.OrderBillDTO;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Entity.Orders;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Entity.Product;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Entity.Users;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Exceptions.*;
import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Repository.OrderRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.time.DateUtils;
@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    EmailService emailService;

    public OrderBillDTO placeOrder (PlaceOrderDTO placeOrderDTO){
        if(userService.findById(placeOrderDTO.getUid())==null){
            throw new UserNotFoundException("user not found");
        }

        Users user=userService.findById(placeOrderDTO.getUid());
        List<Product> productList=new ArrayList<>();

        int price=0;

        for(int ele:placeOrderDTO.getList()){
            Product product=productService.findById(ele);

            if(product==null){
                throw new ProductNotFoundException("product with pid "+ele+" does not exist");
            }


            price+=product.getPrice();
            productList.add(product);
        }

        Orders order=new Orders();
        order.setTotalOrderItems(placeOrderDTO.getList().size());
        order.setTotalOrderPrice(price);
        order.setDelivered(false);
        order.setUser(user);
        Date date=new Date();
         date=DateUtils.addDays(date,7);
         order.setEstimatedDelivery(date);
         orderRepository.save(order);

        for(int ele:placeOrderDTO.getList()){


            orderRepository.mapOrderIDVsProductID(order.getId(),ele);


        }


        OrderBillDTO orderBillDTO=new OrderBillDTO();
        orderBillDTO.setProducts(productList);
        orderBillDTO.setTotalPrice(price);

        emailService.sendOrderPlaceMail(user,order);

        return orderBillDTO;
    }

    public List<Orders> findAllOrdersByUid(int uid){
        if(userService.findById(uid)==null){
            throw new UserNotFoundException("user not found");
        }
        List<Orders> list= orderRepository.findAllOrdersByUid(uid);

        if(list.size()==0){
            throw new NoOrderPacedYetException(String.format("No order placed by %d till yet",uid));
        }
        return list;
    }

    public List<Orders> findOrdersByDeliveryPreference(int uid, String isDelivered){
            if(userService.findById(uid)==null){
                throw new UserNotFoundException("user not found");
            }
            else if(isDelivered.equals("Delivered")){
                return orderRepository.findDeliveredOrdersByUid(uid);
            }
            else if(isDelivered.equals("NotDelivered")){
                return  orderRepository.findNotDeliveredOrdersByUid(uid);
            }
            else{
                throw new WrongDeliveryPreferenceException("Delivery preference is not correct");
            }
    }

    public void cancelOrder(int oid, int uid){
        if(userService.findById(uid)==null){
            throw new UserNotFoundException("user not found");
        }

        if(orderRepository.findById(oid).orElse(null)==null){
            throw new OrderNotFountException(String.format("order with oid %d not present in database",oid));
        }
        List<Orders> list=orderRepository.checkUserHaveOrder(oid,uid);

        if(list.size()>0){
            orderRepository.deleteOrderFromOIDVsUidTable(oid);
            orderRepository.cancelOrderByUser(oid,uid);
        } else if (userService.findById(uid).isAdminApprove()==true) {
            orderRepository.deleteOrderFromOIDVsUidTable(oid);
            orderRepository.cancelOrderByAdmin(oid);
        }
        else{
            throw new WrongAccessException("to cancel orders of another user need Admin access");
        }
    }
}
