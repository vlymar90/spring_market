package com.lymar.gb.my_market.service;



import com.lymar.gb.my_market.entity.Order;
import com.lymar.gb.my_market.entity.Product;
import com.lymar.gb.my_market.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@SessionScope
public class CartService {
    private List<Product> products;
    private OrderRepository orderRepository;

    @Autowired
    public CartService(OrderRepository orderRepository) {
       this.orderRepository = orderRepository;
    }

    @PostConstruct
    private void initList() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void addProduct(Set<Product> products) {
        this.products.addAll(products.stream().map(
                product -> {
                    var newProduct = new Product();
                    newProduct.setCount(1);
                    newProduct.setName(product.getName());
                    newProduct.setId(product.getId());
                    return newProduct;
                }
        ).collect(Collectors.toList()));
    }

    public void deleteProduct(Product product) {
        products.removeIf(p -> p.getId().equals(product.getId()));
    }

    public void increaseProductCount(Product product) {
        for(Product innerProduct: products) {
            if(product.getId().equals(innerProduct.getId())) {
                innerProduct.incrementCount();
                return;
            }
        }
    }

    public void decreaseProductCount(Product product) {
        for(Product innerProduct: products) {
            if(product.getId().equals(innerProduct.getId())) {
                innerProduct.decreaseCount();
                return;
            }
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public void makeOrder(Long idUser) {
        int id = Math.toIntExact(idUser);
        List<Order> list = products.stream()
                .map(i -> new Order(Math.toIntExact(i.getId()), i.getCount(), id))
                .collect(Collectors.toList());
        orderRepository.saveAll(list);
    }
}


