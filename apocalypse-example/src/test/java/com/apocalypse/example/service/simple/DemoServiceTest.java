package com.apocalypse.example.service.simple;

import com.alibaba.fastjson.JSONObject;
import com.apocalypse.common.enums.OperatorEnum;
import com.apocalypse.common.enums.SexEnum;
import com.apocalypse.example.ExampleApplication;
import com.apocalypse.example.model.DemoModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * @author jingkaihui
 * @since 2018/12/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleApplication.class)
public class DemoServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoServiceTest.class);

    @Autowired
    private DemoService demoService;

    @Test
    public void insert() {
        DemoModel dm = new DemoModel();
        dm.setName("机智的小哪吒");
        dm.setBirthday(LocalDate.now());
        dm.setSex(SexEnum.MALE.getValue());
        JSONObject extend = new JSONObject();
        extend.fluentPut("job", "程序员").fluentPut("hobby", new String[]{"唱歌", "跑步", "爬山"})
                .fluentPut("salary", 3500);
        dm.setExtend(extend);
        dm.setFcd(LocalDateTime.now());
        dm.setFcu(OperatorEnum.TASK.name());
        dm.setLmd(LocalDateTime.now());
        dm.setLmu(OperatorEnum.TASK.name());
        demoService.insert(dm);
    }

    @Test
    public void insertSelective() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void deleteByPrimaryKey() {
    }

    @Test
    public void updateByPrimaryKey() {
    }

    @Test
    public void updateByPrimaryKeySelective() {
    }

    @Test
    public void selectOne() {
    }

    @Test
    public void select() {
    }

    @Test
    public void selectAll() {
    }

    @Test
    public void selectCount() {
    }

    @Test
    public void selectByPrimaryKey() {
        DemoModel dm = demoService.selectByPrimaryKey(4);
        LOGGER.info(JSONObject.toJSONString(dm, true));
    }

    @Test
    public void existsWithPrimaryKey() {
    }

    @Test
    public void selectByExample() {
    }

    @Test
    public void selectCountByExample() {
    }

    @Test
    public void deleteByExample() {
    }

    @Test
    public void updateByExample() {
    }

    @Test
    public void updateByExampleSelective() {
    }

    @Test
    public void insertList() {
    }

    @Test
    public void insertUseGeneratedKeys() {
    }
}