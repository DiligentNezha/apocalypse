package com.apocalypse.example.mapper.complex;

import com.apocalypse.example.ExampleApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/5/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleApplication.class)
public class ExampleUnionExampleExtendMapperTest {

    @Autowired
    private ExampleUnionExampleExtendMapper exampleDOManagerMapper;

    @Test
    public void unionSelectUseAnnotation() {
        Map test = exampleDOManagerMapper.unionSelectUseAnnotation(1134296278184890368L);
        System.out.println(test);
    }

    @Test
    public void unionSelectUseXml() {
        Map test = exampleDOManagerMapper.unionSelectUseXml(1134296278184890368L);
        System.out.println(test);
    }
}