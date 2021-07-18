package com.lymar.gb.my_market;

import com.lymar.gb.my_market.entity.Product;
import com.lymar.gb.my_market.service.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.util.*;

@SpringBootTest
public class CartServiceTest {
    @Autowired
    private CartService cartService;

    @BeforeEach
    public void init() {
        for (long i = 0; i < 10; i++) {
            Product product = new Product();
            product.setId(i);
            product.setName("Name" + i);
            product.setPrice(BigDecimal.valueOf((Math.random() * 100)));
            cartService.getProducts().add(product);
        }
    }
    @Test
    public void addCart() {
        cartService.addProduct(new Product());
        Assertions.assertEquals(11, cartService.getProducts().size());
    }

    @Test
    public void delete() {
        cartService.deleteProduct(cartService.getProducts().get(0));
        Assertions.assertEquals(9, cartService.getProducts().size());
    }

    @Test
    public void addSet() {
        Set<Product> products = new HashSet<>(List.of(new Product(), new Product(), new Product()));
        cartService.addProduct(products);
        Assertions.assertEquals(13, cartService.getProducts().size());

    }
}
