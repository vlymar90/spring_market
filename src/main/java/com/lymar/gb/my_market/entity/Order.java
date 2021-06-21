package com.lymar.gb.my_market.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_user")
@NoArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int product_id;
    private int count;
    private int user_id;

    public Order(int product_id, int count, int user_id) {
        this.product_id = product_id;
        this.count = count;
        this.user_id = user_id;
    }
}
