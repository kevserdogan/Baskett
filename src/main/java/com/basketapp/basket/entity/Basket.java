package com.basketapp.basket.entity;



import com.basketapp.basketappproduct.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;


@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Basket {

    private Product product;

    private String userId;
    private BasketInfo basketInfo;
    private int quantity;
    private BasketStatus basketStatus;
    @Id
    private String id;
}
