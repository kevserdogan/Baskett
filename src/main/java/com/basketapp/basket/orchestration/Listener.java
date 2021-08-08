package com.basketapp.basket.orchestration;

import com.basketapp.basket.entity.Basket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class Listener {
    @Autowired
    private final BasketService basketService;
    private final EMailServ eMailServ;
    private final BasketProcess basketProcess;
    public Listener(BasketService basketService, EMailServ eMailServ, BasketProcess basketProcess) {
        this.basketService = basketService;
        this.eMailServ = eMailServ;
        this.basketProcess = basketProcess;
    }

    @KafkaListener(topics = "${kafka.topic.productPriceChange}", groupId = "${kafka.groupId}")
    public void productPriceChangeListener(@Payload String serialNumber) {

        List<Basket> allBasket = basketService.getAllBasketData();
        allBasket=allBasket.stream().filter(x -> Objects.equals(x.getProduct().getSerialNumber(), serialNumber)).collect(Collectors.toList());
        allBasket.stream().forEach(basketData -> {
            eMailServ.sendSimpleMessage(basketData.getUserId(),"Fiyat Düştü","User : " + basketData.getUserId() + " -> Product : " + basketData.getProduct().getName() + "'ın fiyatı değişti" );
            System.out.println("User : " + basketData.getUserId() + " -> Product : " + basketData.getProduct().getName() + "'ın fiyatı değişti");
        });
    }

    @KafkaListener(topics = "${kafka.topic.stockDecreased}", groupId = "${kafka.groupId}")
    public void productStockDecreasedListener(@Payload String serialNumber) {

        List<Basket> allBasket = basketService.getAllBasketData();
        allBasket=allBasket.stream().filter(x -> Objects.equals(x.getProduct().getSerialNumber(), serialNumber)).collect(Collectors.toList());
        allBasket.stream().forEach(basketData -> {
           eMailServ.sendSimpleMessage(basketData.getUserId(),"Stok Azaldı","User : " + basketData.getUserId() + " -> Product : " + basketData.getProduct().getName() + "'ın stoğu azaldı");
            System.out.println("User : " + basketData.getUserId() + " -> Product : " + basketData.getProduct().getName() + "stok azaldı");
        });
    }

    @KafkaListener(topics = "${kafka.topic.soldOut}", groupId = "${kafka.groupId}")
    public void productSoldOutListener(@Payload String serialNumber) {

        List<Basket> allBasket = basketService.getAllBasketData();
        allBasket=allBasket.stream().filter(x -> Objects.equals(x.getProduct().getSerialNumber(), serialNumber)).collect(Collectors.toList());
        allBasket.stream().forEach(basketData -> {

            basketData=basketProcess.removeProductInBasket(basketData);
            basketData.setQuantity(0);
            basketService.deleteBasketByBasket(basketData);

          eMailServ.sendSimpleMessage(basketData.getUserId(),"Tükendi","User : " + basketData.getUserId() + " -> Product : " + basketData.getProduct().getName() + "'ürün stoğu tükendi.");
            System.out.println("User : " + basketData.getUserId() + " -> Product : " + basketData.getProduct().getName() + "'stok tükendi");
        });
    }
}
