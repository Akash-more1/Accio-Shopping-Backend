package com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Repository;

import com.acciojobsshopping.backend.Accio.shopping.Website.Backend.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
}
