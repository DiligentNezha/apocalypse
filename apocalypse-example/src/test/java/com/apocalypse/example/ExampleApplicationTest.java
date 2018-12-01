package com.apocalypse.example;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

/**
 * @author jingkaihui
 * @since 2018/12/1
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleApplication.class)
public class ExampleApplicationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleApplicationTest.class);

    @Autowired
    private DruidDataSource druidDataSource;

    @Test
    public void dateSource() throws SQLException {
        LOGGER.info("方法名:{};参数:{};信息:{}", "dateSource", "[]",
                JSONObject.toJSONString(druidDataSource.getCreateCount(), true));
    }
}