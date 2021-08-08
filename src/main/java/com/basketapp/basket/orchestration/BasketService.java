package com.basketapp.basket.orchestration;

import com.basketapp.basket.entity.Basket;
import com.basketapp.basket.repository.BasketRepository;
import com.basketapp.basketappproduct.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BasketService {
    private final BasketRepository basketRepository;

    public BasketService(BasketRepository basketRepository)
    {
        this.basketRepository=basketRepository;
    }

    public List<Basket> getBasketByUserId(String userId)
    {
        return basketRepository.getBasketByUserId(userId);
    }

    public List<Basket> getBasketByProductAndUserId(Product product,String userId)
    {
        return basketRepository.getBasketByProductAndUserId(product,userId);
    }

    public void addBasketByUserId(Basket basket,String userId)
    {
        basket.setUserId(userId);
        basketRepository.save(basket);
    }
    public List<Basket> getAllBasketData()
    {
        return basketRepository.findAll();
    }

    public void  updateBasketByUserId(Basket basket ,String userId)
    {
        basket.setUserId(userId);
        basketRepository.save(basket);
    }

    public void deleteBasketByBasket(Basket basket)
    {
            basketRepository.delete(basket);

    }
    public void deleteAllBasket(String userId)
    {
        basketRepository.deleteAll();//TODO userId ile değiştirilecek.
    }
}
