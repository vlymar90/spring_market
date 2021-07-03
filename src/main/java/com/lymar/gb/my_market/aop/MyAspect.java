package com.lymar.gb.my_market.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAspect {

    @Around("@annotation(myLogic)")
    protected Object myAround(ProceedingJoinPoint point, MyLogic myLogic) throws Throwable {
        System.out.println("Всё голову сломал, какую логику сюда запихать," +
                "никаких идей не появилось. Вот поэтому вывожу просто sout.");
        point.proceed();
        return point;
    }
}
