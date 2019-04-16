package com.apocalypse.example.java8;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author <a href="jingkaihui@adpanshi.com">jingkaihui</a>
 * @Description
 * @date 2019/4/16
 */
@Slf4j
public class OptionalTest {

    @Test
    public void ofTest() {
        Optional<Integer> optional = Optional.of(10);
        log.info("optional:[{}]", optional.get());
    }

    @Test
    public void ofNullable() {
        Optional<Object> optional = Optional.ofNullable(null);
        log.info("optional orElse:[{}]", optional.orElse(1));
        log.info("optional orElseGet:[{}]", optional.orElseGet(() -> RandomUtil.randomInt()));
    }


}
