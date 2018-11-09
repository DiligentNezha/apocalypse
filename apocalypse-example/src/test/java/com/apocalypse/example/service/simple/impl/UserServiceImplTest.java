package com.apocalypse.example.service.simple.impl;

import com.alibaba.fastjson.JSONObject;
import com.apocalypse.example.ExampleApplication;
import com.apocalypse.example.model.UserModel;
import com.apocalypse.example.service.simple.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author 景凯辉
 * @date 2018/11/9
 * @mail jingkaihui@adpanshi.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleApplication.class)
public class UserServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImplTest.class);

    @Autowired
    private UserService userService;

    @Test
    public void insert() {
        UserModel userModel = new UserModel();
        userModel.setName("机智的小哪吒");
        userModel.setRealName("景凯辉");
        userModel.setAge((byte) 23);

        int insert = userService.insert(userModel);
        logger.info("insert success : " + (1 == insert));
    }

    @Test
    public void insertSelective() {
        UserModel userModel = new UserModel();
        userModel.setName("机智的小哪吒");
        userModel.setAge((byte) 23);
        int insert = userService.insertSelective(userModel);
        logger.info("insert success : " + (1 == insert));
    }

    @Test
    public void delete() {
        UserModel userModel = new UserModel();
        userModel.setAge((byte) 23);
        int delete = userService.delete(userModel);
        logger.info("delete success : " + (delete > 0));
    }

    @Test
    public void deleteByPrimaryKey() {
        int delete = userService.deleteByPrimaryKey(1);
        logger.info("delete success : " + (delete > 0));
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
        UserModel userModel = userService.selectByPrimaryKey(2);
        logger.info(JSONObject.toJSONString(userModel, true));
    }

    @Test
    public void existsWithPrimaryKey() {
        boolean b = userService.existsWithPrimaryKey(1);
        logger.info("exist : " + b);
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