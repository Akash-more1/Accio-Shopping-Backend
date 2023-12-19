package com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Repository;

import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Entity.Orders;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

    @Transactional
    @Modifying
    @Query(value = "insert into orders_order_items (orders_id,order_items_pid) values(:oid,:pid)", nativeQuery = true)
    public void mapOrderIDVsProductID(int oid, int pid);

    @Query(value = "select * from orders where user_uid=:uid", nativeQuery = true)
    public List<Orders> findAllOrdersByUid(int uid);

    @Query(value = "select * from orders where user_uid=:uid and is_delivered=false", nativeQuery = true)
    public List<Orders> findNotDeliveredOrdersByUid(int uid);

    @Query(value = "select * from orders where user_uid=:uid and is_delivered=true", nativeQuery = true)
    public List<Orders> findDeliveredOrdersByUid(int uid);

    //three methods to implement cancel order service
    @Query(value = "select * from orders where id=:oid and user_uid=:uid",nativeQuery = true)
    public List<Orders> checkUserHaveOrder(int oid, int uid);

    @Transactional
    @Modifying
    @Query(value = "delete from orders_order_items where orders_id=:oid",nativeQuery = true)
    public  void deleteOrderFromOIDVsUidTable(int oid);

    @Transactional
    @Modifying
    @Query(value = "delete  from orders where id=:oid and user_uid=:uid",nativeQuery = true)
    public void cancelOrderByUser(int oid, int uid);

    @Transactional
    @Modifying
    @Query(value = "delete  from orders where id=:oid",nativeQuery = true)
    public  void cancelOrderByAdmin(int oid);
}
