package com.apocalypse.example.mapper.simple;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apocalypse.example.ExampleApplication;
import com.apocalypse.example.model.ExampleDO;
import com.apocalypse.example.model.ExampleExtendDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/5/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleApplication.class)
public class ExampleDOMapperTest {

    @Autowired
    private ExampleDOMapper exampleDOMapper;

    @Autowired
    private ExampleExtendDOMapper exampleExtendDOMapper;

    @Test
    public void save() {
        ExampleExtendDO exampleExtendDO = new ExampleExtendDO()
                .setAlias("张人风")
                .setRemark("江阿生易容前的名字");

        exampleExtendDOMapper.insertSelective(exampleExtendDO);

        ExampleDO exampleDO = new ExampleDO()
                .setExampleExtendId(exampleExtendDO.getId())
                .setName("江阿生")
                .setBirthday(LocalDate.now())
                .setHobby(new JSONArray()
                        .fluentAdd("唱歌")
                        .fluentAdd("舞剑"))
                .setExtend(new JSONObject()
                        .fluentPut("job", "programmer")
                        .fluentPut("tag", "talk is cheap! show me the code!"))
                .setRemark("低调！低调！低调！重要的事情说三遍！");

        exampleDOMapper.insertSelective(exampleDO);
    }
}