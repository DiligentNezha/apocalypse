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
            if (RandomUtil.randomInt() % 2 == 0) {
                logger.warn("具体业务异常描述");
                throw new ServiceException("200002", "具体业务异常描述");
            }
        } catch (ServiceException e) {
            logger.error(e.getMsg(), e);
            throw new ServiceException(e.getCode(), e.getMsg(), e);
        } catch (Exception e) {
            logger.error("获取用户异常", e);
            throw new ServiceException("100002", "获取用户异常", e);
        }
        return user;
    }
}
