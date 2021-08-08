package com.basketapp.basket.orchestration;

import com.basketapp.basket.entity.Basket;
import com.basketapp.basket.entity.BasketInfo;
import com.basketapp.basket.entity.Constant;
import com.basketapp.basketappproduct.entity.Product;
import org.springframework.stereotype.Service;
@Service
public class BasketProcess {

private final BasketInfo basketInfo=new BasketInfo();



    public BasketInfo calculateAddBasketInfo(Basket basket)
    {
        // TODO optional gelmemeli ya da optional dönebilmeli.
        double subTotal=basket.getProduct().getPrice()*basket.getQuantity();
        if(subTotal< Constant.cargolimit)
        {
            basketInfo.setCargoPrice(Constant.carcgoPrice);
            basketInfo.setTotalPrice(subTotal+Constant.carcgoPrice);
        }
        else
        {
            basketInfo.setTotalPrice(subTotal);
        }
        basketInfo.setSubTotalPrice(subTotal);
        return basketInfo;
    }

    public Basket removeProductInBasket(Basket basket)
    {
        Product removedProduct=basket.getProduct();
        double totalPrice = removedProduct.getPrice()*basket.getQuantity();
        double remainPrice =totalPrice- basket.getProduct().getPrice();
        if(remainPrice<=0)
        {
            basket.getBasketInfo().setSubTotalPrice(0);
            basket.getBasketInfo().setCargoPrice(0);
            basket.getBasketInfo().setTotalPrice(0);
        }
        removedProduct.setPrice(remainPrice);
        basket.setProduct(removedProduct);
        return  basket;
    }
}
