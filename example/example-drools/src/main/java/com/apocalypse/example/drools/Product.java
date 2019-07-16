package com.apocalypse.example.drools;

import lombok.Data;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/7/15
 */
@Data
public class Product {
    /**
     * 商品名
     */
    private String name;

    /**
     * 商品定价
     */
    private double prePrice;

    /**
     * 实际售价
     */
    private double realPrice;

    public Product() {
    }

    public Product(String name, double prePrice) {
        this.name = name;
        this.prePrice = prePrice;
    }
}
