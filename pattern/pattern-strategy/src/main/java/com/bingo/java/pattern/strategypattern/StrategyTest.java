package com.bingo.java.pattern.strategypattern;

import java.util.Arrays;

/**
 * @author Bingo
 * @title: StrategyTest
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/8  23:18
 */
public class StrategyTest {

    public static void main(String[] args) {
        GoodsOrder order = new GoodsOrder("order-id", Arrays.asList("goodsId"),700.00);
        Wallet wallet = new Wallet("uid");
        wallet.pay(order, "Ali");
    }
}
