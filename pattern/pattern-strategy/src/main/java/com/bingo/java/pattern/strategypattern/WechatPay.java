package com.bingo.java.pattern.strategypattern;

/**
 * @author Bingo
 * @title: AliPay
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/8  23:02
 */
public class WechatPay implements Pay {
    private double balance;

    public WechatPay(String uid) {
        balance = getBalance(uid);
    }

    @Override
    public PayResult pay(GoodsOrder order) {
        PayResult result = null;
        if (order.totalPrice < balance) {
            result = new PayResult("200", String.format("微信支付成功，信息如下：订单号：%s,总价格：%f", order.orderId, order.totalPrice), null);
            balance = balance - order.totalPrice;
        }
        result = new PayResult("400", null, "支付失败");
        return result;
    }

    @Override
    public double getBalance(String uid) {
        return 200;
    }
}
