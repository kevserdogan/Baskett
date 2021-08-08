package com.basketapp.basket.controller;

import com.basketapp.basket.entity.Basket;
import com.basketapp.basket.entity.BasketStatus;
import com.basketapp.basket.orchestration.BasketProcess;
import com.basketapp.basket.orchestration.BasketService;

import com.basketapp.basketappproduct.entity.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/basket")
public class BasketController {
    private final BasketService basketService;
    private final BasketProcess basketProcess;
    public BasketController(BasketService basketService, BasketProcess basketProcess)
    {
        this.basketService=basketService;
        this.basketProcess = basketProcess;
    }
    @GetMapping("/{userId}")
    public List<Basket> getBasketByUserId(@PathVariable String userId)
    {
        return basketService.getBasketByUserId(userId);
    }

    @PostMapping("/{userId}")
    public void addBasketByUserId(@RequestBody Basket basketData, @PathVariable String userId)
    {
       List<Basket> basket =basketService.getBasketByUserId(userId);
       Product addedProduct=basketData.getProduct();
       Basket fillBasket =basket.stream().filter(x-> Objects.equals(x.getProduct().getSerialNumber(),basketData.getProduct().getSerialNumber())).findFirst().orElse(null);

       if(fillBasket!=null)
       {
           fillBasket.setProduct(addedProduct);
           fillBasket.setQuantity(fillBasket.getQuantity()+1);
           fillBasket.setBasketInfo(basketProcess.calculateAddBasketInfo(fillBasket));
           fillBasket.setBasketStatus(BasketStatus.InBasket);
           basketService.addBasketByUserId(fillBasket,userId);
       }
       else
       {
           basketData.setQuantity(1);
           basketData.setBasketStatus(BasketStatus.InBasket);
           basketData.setBasketInfo(basketProcess.calculateAddBasketInfo(basketData));
           basketData.setId(UUID.randomUUID().toString());
           basketService.addBasketByUserId(basketData,userId);
       }



    }



    @PutMapping("/{userId}")
    public void removeProductOnBasket(@RequestBody Product product,@PathVariable String userId)
    {
        List<Basket> basket =basketService.getBasketByUserId(userId);


            Basket fillBasket = basket.stream().filter(x -> Objects.equals(x.getProduct().getSerialNumber(), product.getSerialNumber())).findFirst().orElse(null);
            if (fillBasket != null && fillBasket.getQuantity() > 1) {
                fillBasket.setQuantity(fillBasket.getQuantity() - 1);
                fillBasket.setBasketInfo(basketProcess.calculateAddBasketInfo(basketProcess.removeProductInBasket(fillBasket)));
                fillBasket.setBasketStatus(BasketStatus.InBasket);
                basketService.updateBasketByUserId(fillBasket, userId);
            } else {
                fillBasket=basketProcess.removeProductInBasket(fillBasket);
                fillBasket.setQuantity(0);
                basketService.deleteBasketByBasket(fillBasket);

            }
    }
}
