package com.bingo.java.pattern.strategypattern;

/**
 * @author Bingo
 * @title: Wallet
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/8  23:11
 */
public class Wallet {
    String uid;

    Pay pay;

    public Wallet(String uid) {
        this.uid = uid;
    }

    public PayResult pay(GoodsOrder order, String payCode) {
        PayResult result = null;
        if (payCode.equals("Ali")) {
            pay = new AliPay(uid);
        } else if (payCode.equals("Apple")) {
            pay = new ApplePay(uid);
        } else if (payCode.equals("Mi")) {
            pay = new MiPay(uid);
        } else if (payCode.equals("Wechat")) {
            pay = new WechatPay(uid);
        }
        result = pay.pay(order);
        System.out.println(result);
        return result;
    }


}
