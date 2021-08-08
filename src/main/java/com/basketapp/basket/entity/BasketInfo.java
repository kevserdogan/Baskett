package com.basketapp.basket.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.couchbase.core.mapping.Document;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasketInfo {

    private double subTotalPrice;
    private double cargoPrice;
    private  double totalPrice;
}
