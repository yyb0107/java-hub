package com.bingo.java.pattern.strategypattern;

public interface Pay {

    PayResult pay(GoodsOrder order);

    double getBalance(String uid);
}
