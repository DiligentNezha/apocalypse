package com.apocalypse.example.service.complex;

import com.apocalypse.example.ExampleApplication;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleApplication.class)
public class TransactionalExampleServiceTest {

    @Autowired
    private TransactionalExampleService transactionalExampleService;

    @Test
    public void transactionalUseAnnotation() {
        transactionalExampleService.transactionalUseAnnotation();
    }

    @Test
    public void transactionalUseProgramming() {
        transactionalExampleService.transactionalUseProgramming();
    }
}