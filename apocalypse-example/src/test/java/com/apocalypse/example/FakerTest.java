package com.apocalypse.example;

import com.alibaba.fastjson.JSONObject;
import com.github.javafaker.Faker;

import java.util.Locale;

/**
 * @author jingkaihui
 * @since 2018/11/27
 */
public class FakerTest {
    @org.junit.Test
    public void test() {
        Faker faker = new Faker(Locale.CHINA);
        String name = faker.name().fullName(); // Miss Samanta Schmidt
        String firstName = faker.name().firstName(); // Emory
        String lastName = faker.name().lastName(); // Barton

        String streetAddress = faker.address().streetAddress(); // 60018 Sawayn Brooks Suite 449
        System.out.println(JSONObject.toJSONString(faker.address().city(), true));
        System.out.println(faker.friends().location());

    }
}
