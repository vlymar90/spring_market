package com.lymar.gb.my_market.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "review")
@NoArgsConstructor
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

//            <column name="text" type="text"/>
//            <column name="product_id" type="int">
//                <constraints nullable="false" foreignKeyName="fk_product_id" references="product(id)"/>
//            </column>
//        </createTable>
}
