package com.eetig.study0714;

import lombok.Data;

/**
 * @title: Order
 * @author: xhong103
 * @date: 2022/7/14 16:17
 */

@Data
public class Order {
    private String orderId;

    private String goods;

    public Order(String orderId, String goods) {
        this.orderId = orderId;
        this.goods = goods;
    }
}
