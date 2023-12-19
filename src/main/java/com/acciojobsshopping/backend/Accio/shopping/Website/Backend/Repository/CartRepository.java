package com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Repository;

import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Entity.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {

    @Query(value = "select * from cart where user_uid=:uid",nativeQuery = true)
    public List<Object[]> findCIByUid(int uid);

    @Modifying
    @Transactional
    @Query(value = "insert into cart_products (cart_cid,products_pid) values(:ci,:pid)",nativeQuery = true)
    public void insertCiVsPid(int ci, int pid);

    @Modifying
    @Transactional
    @Query(value = "update cart set total_items=:quantity, total_price=:price where cid=:cartId",nativeQuery = true )
    public void updateQuantityAnsPriceOfCart(int quantity, int price,int cartId);

    @Query(value = "select products_pid from cart_products where cart_cid=:cid",nativeQuery = true)
   public List<Object[]> findAllProductsInCart(int cid);

    @Modifying
    @Transactional
    @Query(value = "delete from cart_products where products_pid=:pid",nativeQuery = true)
    public void deleteProductFromCart(int pid);
}
