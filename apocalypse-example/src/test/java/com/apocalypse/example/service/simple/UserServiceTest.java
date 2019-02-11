package com.apocalypse.example.service.simple;

import com.alibaba.fastjson.JSONObject;
import com.apocalypse.common.exception.EmptyingDataException;
import com.apocalypse.example.ExampleApplication;
import com.apocalypse.example.dto.LoginInfoDTO;
import com.apocalypse.example.model.UserModel;
import com.apocalypse.example.model.UserModelExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

/**
 * @author jingkaihui
 * @since 2018/12/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleApplication.class)
public class UserServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

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
        userModel.setAge((byte) 22);
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
        UserModel userModel = new UserModel();
        userModel.setId(2);
        userModel.setAge((byte) 23);
        int i = userService.updateByPrimaryKey(userModel);
        logger.info("update success : " + i);
    }

    @Test
    public void updateByPrimaryKeySelective() {
        UserModel userModel = new UserModel();
        userModel.setId(2);
        userModel.setAge((byte) 23);
        int i = userService.updateByPrimaryKeySelective(userModel);
        logger.info("update success : " + i);
    }

    @Test
    public void selectOne() {
        UserModel userModel = new UserModel();
        userModel.setId(2);
        userModel.setAge((byte) 25);
        UserModel um = userService.selectOne(userModel);
        logger.info(JSONObject.toJSONString(um, true));
    }

    @Test
    public void select() {
        UserModel userModel = new UserModel();
        userModel.setName("景凯辉");
        List<UserModel> models = userService.select(userModel);
        for (UserModel model : models) {
            logger.info(JSONObject.toJSONString(model, true));
        }
    }

    @Test
    public void selectAll() {
        List<UserModel> models = userService.selectAll();
        for (UserModel model : models) {
            logger.info(JSONObject.toJSONString(model, true));
        }
    }

    @Test
    public void selectCount() {
        UserModel userModel = new UserModel();
        int i = userService.selectCount(userModel);
        logger.info("count : " + i);
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
        UserModelExample userModelExample = new UserModelExample();
        UserModelExample.Criteria criteria1 = userModelExample.createCriteria();

        Example example = new Example(UserModel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", "机智的小哪吒28");
        List<UserModel> list = userService.selectByExample(example);
        for (int i = 0; i < list.size(); i++) {
            logger.info(JSONObject.toJSONString(list.get(i), true));
        }
    }

    @Test
    public void selectCountByExample() {
        Example example = new Example(UserModel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", "机智的小哪吒28");
        int i = userService.selectCountByExample(example);
        logger.info("count : " + i);
    }

    @Test
    public void deleteByExample() {
        Example example = new Example(UserModel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", "机智的小哪吒28");
        int delete = userService.deleteByExample(example);
        logger.info("delete : " + delete);
    }

    @Test
    public void updateByExample() {
        Example example = new Example(UserModel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", "机智的小哪吒32");

        UserModel userModel = new UserModel();
        userModel.setName("机智的小哪吒28");

        int i = userService.updateByExample(userModel, example);
        logger.info("update success " + i);

    }

    @Test
    public void updateByExampleSelective() {
        Example example = new Example(UserModel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", "机智的小哪吒28");

        UserModel userModel = new UserModel();
        userModel.setName("机智的小哪吒32");

        int i = userService.updateByExampleSelective(userModel, example);
        logger.info("update success " + i);
    }

    @Test
    public void insertList() {
        Random random = new Random();
        List<UserModel> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            UserModel userModel = new UserModel();
            userModel.setName("机智的小哪吒" + random.nextInt(1000));
            userModel.setRealName("景凯辉" + random.nextInt(1000));
            userModel.setAge((byte) (20 + random.nextInt(5)));
            userModel.setSex((byte) random.nextInt(3));
            list.add(userModel);
        }
        int i = userService.insertList(list);
        logger.info("insert success : " + i);
    }

    @Test
    public void insertUseGeneratedKeys() {
        UserModel userModel = new UserModel();
        Random random = new Random();
        userModel.setName("机智的小哪吒" + random.nextInt(1000));
        userModel.setRealName("景凯辉" + random.nextInt(1000));
        userModel.setAge((byte) (20 + random.nextInt(5)));
        userModel.setSex((byte) random.nextInt(3));
        int key = userService.insertUseGeneratedKeys(userModel);
        logger.info("insert success : " + key);
    }
    @Test
    public void selectByIds() {
        List<UserModel> list = userService.selectByIds("10,11,12");
        for (UserModel userModel : list) {
            System.out.println(JSONObject.toJSONString(userModel, true));
        }
    }

    @Test
    public void deleteByIds() {
        userService.deleteByIds("10,11");
    }

    @Test
    public void login() {
        LoginInfoDTO loginInfoDTO = new LoginInfoDTO();
        loginInfoDTO.setMail("499508968@qq.com");
        loginInfoDTO.setPassword("1234567");
        LoginInfoDTO login = userService.login(loginInfoDTO);
        System.out.println(JSONObject.toJSONString(login, true));
    }
}