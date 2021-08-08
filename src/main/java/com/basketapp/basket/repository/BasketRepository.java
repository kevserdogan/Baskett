package com.basketapp.basket.repository;


import com.basketapp.basket.entity.Basket;
import com.basketapp.basketappproduct.entity.Product;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BasketRepository extends CouchbaseRepository<Basket, String> {

    public List<Basket> getBasketByUserId(String userId);
    @Query("#{#n1ql.selectEntity} where #{#n1ql.filter} and product = $1 and $2 ")
    public List<Basket> getBasketByProductAndUserId(Product product,String userId);
}

