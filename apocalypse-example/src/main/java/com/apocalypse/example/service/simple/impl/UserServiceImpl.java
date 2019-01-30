package com.apocalypse.example.service.simple.impl;

import cn.hutool.core.util.RandomUtil;
import com.apocalypse.common.exception.ServiceException;
import com.apocalypse.common.service.impl.BaseServiceImpl;
import com.apocalypse.example.mapper.simple.UserModelMapper;
import com.apocalypse.example.model.UserModel;
import com.apocalypse.example.service.simple.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 景凯辉
 * @date 2018/11/9
 * @mail kaihuijing@gmail.com
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends BaseServiceImpl<UserModel> implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserModelMapper userModelMapper;

    @Override
    public UserModel getUser(String userId) throws ServiceException{
        UserModel user = null;
        try {
            user = userModelMapper.getUser(userId);
            //处理业务时，预知到的异常
            if (RandomUtil.randomInt() % 2 == 0) {
                logger.warn("具体业务异常描述信息（此处会记录在日志中）");
                throw new ServiceException("200002", "具体业务异常描述（此处会返回给前端）");
            }
            //处理业务时，抛出可能未预知到的异常
            int i = 10 / RandomUtil.randomInt(3);
        } catch (Exception e) {
            String template = "获取用户异常（此处会记录在日志中）用户编号:{}，用户姓名:{}";
//            logger.error("获取用户异常:{}", e);
            throw new ServiceException(logger, "100002", "获取用户异常（此处会返回给前端）", e, template, "1021212", "小哪吒");
        }
        return user;
    }
}
