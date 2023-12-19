package com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Repository;

import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Entity.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users,Integer> {

    public Users findByUserName(String userName);

    @Query(value = "select count(*) from users where role=\"Admin\";",nativeQuery = true)
    public List<Object[]> findAdmin();

    @Transactional
    @Modifying
    @Query(value = "update users set is_admin_approve=true where uid=:uid",nativeQuery = true)
    public  void approveAdmin(int uid);
}
