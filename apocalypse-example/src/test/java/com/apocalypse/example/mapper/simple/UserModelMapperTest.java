package com.apocalypse.example.mapper.simple;

import com.apocalypse.example.ExampleApplication;
import com.apocalypse.example.model.UserModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 景凯辉
 * @date 2018/11/9
 * @mail jingkaihui@adpanshi.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleApplication.class)
public class UserModelMapperTest {

    @Autowired
    private UserModelMapper userModelMapper;

    @Test
    public void insert() {
        UserModel userModel = new UserModel();
        userModel.setName("机智的小哪吒");
        userModel.setRealName("景凯辉");
        userModel.setAge((byte) 24);
        userModelMapper.insert(userModel);
    }
}