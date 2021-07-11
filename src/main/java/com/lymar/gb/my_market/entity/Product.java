package com.lymar.gb.my_market.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "product")
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private int count;


    public void incrementCount() {
        this.count++;
    }

    public void decreaseCount() {
        this.count--;
    }

    public static StringBuilder converterListToString(List<Product> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for(Product p : list) {
            stringBuilder.append(p.name + ": " + System.lineSeparator()
            + "Количество: " + p.count);
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder;
    }
}
