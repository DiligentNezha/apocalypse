package com.apocalypse.example.java8;

import com.alibaba.fastjson.JSONObject;
import com.apocalypse.example.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="jingkaihui@adpanshi.com">jingkaihui</a>
 * @Description
 * @date 2019/4/16
 */
@Slf4j
public class StreamTest {
    private List<UserModel> users = new ArrayList<>();

    @Before
    public void data() {
        users.add(new UserModel().setId(1).setSex((byte) 1).setName("zhang san").setAge(24));
        users.add(new UserModel().setId(2).setSex((byte) 2).setName("li si").setAge(25));
        users.add(new UserModel().setId(3).setSex((byte) 2).setName("wang wu").setAge(26));
        users.add(new UserModel().setId(4).setSex((byte) 1).setName("ma liu").setAge(24));
        users.add(new UserModel().setId(5).setSex((byte) 2).setName("zhao 1i").setAge(25));
    }

    @Test
    public void filterTest() {
        List<UserModel> userList = users.stream()
                .filter(userModel -> userModel.getName().startsWith("zh"))
                .collect(Collectors.toList());
        assertEquals(2, userList.stream().count());
    }

    @Test
    public void mapTest() {
        List<String> names = users.stream()
                .map(userModel -> userModel.getName())
                .collect(Collectors.toList());
        log.info("names[{}]", names);
    }

    @Test
    public void flatMapTest() {
        List<String> collect = users.stream()
                .map(userModel -> userModel.getName().split(" "))
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
        log.info("collect:[{}]", collect);

        Set<String> set = collect.stream().collect(Collectors.toSet());
        log.info("set:[{}]", set);
    }

    @Test
    public void toMapTest() {
        Map<Integer, String> map = users.stream()
                .collect(Collectors.toMap(UserModel::getId, userModel -> JSONObject.toJSONString(userModel, true)));
        log.info("map:[{}}", map);
    }

    @Test
    public void toTreeSetTest() {
        TreeSet<UserModel> treeSet = users.stream()
                .collect(Collectors.toCollection(TreeSet::new));
        log.info("treeSet:[{}]", treeSet);
    }

    @Test
    public void toGroupTest() {
        Map<Boolean, List<UserModel>> group = users.stream()
                .collect(Collectors.groupingBy(userModel -> userModel.getSex().equals(Byte.valueOf((byte)1))));
        log.info("group:[{}]", group);
    }

    @Test
    public void toJoinTest() {
        String joinStr = users.stream()
                .map(userModel -> userModel.getName())
                .collect(Collectors.joining(",", "{", "}"));
        log.info("join:[{}]", joinStr);
    }

}
