package com.bingo.java.pattern.strategypattern;

import java.util.Arrays;
import java.util.List;

/**
 * @author Bingo
 * @title: GoodsOrder
 * @projectName java-hub
 */
public class GoodsOrder {
    String orderId;
    List<String> goodsId;
    double totalPrice;

    public GoodsOrder(String orderId, double totalPrice) {
        this(orderId, Arrays.asList("default-id"),totalPrice);
    }

    public GoodsOrder(String orderId, List<String> goodsId, double totalPrice) {
        this.orderId = orderId;
        this.goodsId = goodsId;
        this.totalPrice = totalPrice;
    }
}
