package com.lymar.gb.my_market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyMarketApplication.class, args);
    }

}



//        ### 2) Переделать логику работы с корзиной на работу с бд
//        ### 3) Добавить сущность Order, а также написать скрипты на инициализацию этой таблицы