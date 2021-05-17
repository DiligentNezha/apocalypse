package com.apocalypse.common.data.mybatis.plugin;

import com.apocalypse.common.core.util.ContextHolder;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/9/21
 */
@Component
@Intercepts(@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}))
public class OperateIdentityAccountInjectInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
//        Object[] args = invocation.getArgs();
//        MappedStatement ms = (MappedStatement) args[0];
//        SqlCommandType sqlCommandType = ms.getSqlCommandType();
//        Object parameter = args[1];
//
//        Class<?> clazz = parameter.getClass();
//
//        Long identityId = ContextHolder.currentIdentityId();
//        Long accountId = ContextHolder.currentAccountId();
//
//        if (!clazz.getSuperclass().isInstance(Object.class)) {
//            if (parameter instanceof HashMap) {
//                HashMap<?, ?> parameterMap = (HashMap<?, ?>) parameter;
//                if (parameterMap.containsKey("list")) {
//                    List list = (List) parameterMap.get("list");
//                    Field[] realDeclaredFields = null;
//                    for (Object o : list) {
//                        if (realDeclaredFields == null) {
//                            realDeclaredFields = o.getClass().getDeclaredFields();
//                        }
//                        updateField(realDeclaredFields, o, sqlCommandType, identityId, accountId);
//                    }
//                } else if (parameterMap.containsKey("record")) {
//                    Object record = ((HashMap<?, ?>) parameter).get("record");
//                    updateField(record.getClass().getDeclaredFields(), record, sqlCommandType, identityId, accountId);
//                }
//            }
//        } else {
//            updateField(parameter.getClass().getDeclaredFields(), parameter, sqlCommandType, identityId, accountId);
//        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    private void updateField(Field[] declaredFields, Object parameter, SqlCommandType sqlCommandType, Long identityId, Long accountId) throws IllegalAccessException {
        for (Field field: declaredFields){
            String fieldName = field.getName();
            field.setAccessible(true);
            if (sqlCommandType.equals(SqlCommandType.INSERT)) {
                switch (fieldName) {
                    case "createIdentityId" :
                    case "updateIdentityId" :
                        field.set(parameter, identityId); break;
                    case "createAccountId" :
                    case "updateAccountId" :
                        field.set(parameter, accountId); break;
                    default: break;
                }
            } else if (sqlCommandType.equals(SqlCommandType.UPDATE)) {
                switch (fieldName) {
                    case "updateIdentityId" :
                        field.set(parameter, identityId); break;
                    case "updateAccountId" :
                        field.set(parameter, accountId); break;
                    default: break;
                }
            }
        }
    }
}
