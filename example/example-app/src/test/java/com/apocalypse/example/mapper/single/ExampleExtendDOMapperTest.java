package com.apocalypse.example.mapper.single;

import com.apocalypse.example.ExampleApplication;
import com.apocalypse.example.model.ExampleExtendDO;
//import org.junit.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/5/31
 */
//@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleApplication.class)
public class ExampleExtendDOMapperTest {

    @Autowired
    private ExampleExtendDOMapper exampleExtendDOMapper;

//    @Test
    public void save() {
        ExampleExtendDO exampleExtendDO = new ExampleExtendDO()
                .setAlias("张人风")
                .setRemark("江阿生易容前的名字");

        exampleExtendDOMapper.insertSelective(exampleExtendDO);
    }
}