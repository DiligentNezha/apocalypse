package com.apocalypse.common.core.api;

import com.apocalypse.common.core.enums.error.ErrorCodeEnumerable;
import com.apocalypse.common.core.enums.error.SystemErrorCodeEnum;
import com.apocalypse.common.core.exception.ServiceException;
import com.apocalypse.common.core.util.ServiceExceptionUtil;
import io.swagger.annotations.ApiModel;
import javassist.*;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Slf4j
@Accessors(chain = true)
@ApiModel(description = "响应类")
public class Rest<T extends BaseResponse> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Boolean success = Boolean.TRUE;

    private String code = "00000";

    private String msg = SystemErrorCodeEnum.SUCCESS.getMsgTemplate();

    private String tip = SystemErrorCodeEnum.SUCCESS.getTip();

    private T data;

    private static final NoPropertyResponse noPropertyResponse = new NoPropertyResponse();

    public static Rest<BaseResponse> success() {
        return new Rest<>()
                .setData(noPropertyResponse);
    }

    public static Rest<BaseResponse> success(String tip) {
        return new Rest<>()
                .setData(noPropertyResponse)
                .setTip(tip);
    }

    public static <T extends BaseResponse> Rest<T> success(T data) {
        return new Rest<T>()
                .setData(data);
    }

    public static <T extends BaseResponse> Rest<T> success(T data, String tip) {
        return new Rest<T>()
                .setData(data)
                .setTip(tip);
    }



    public static Rest<BaseResponse> error(ErrorCodeEnumerable errorCode, Object... params) {
        return new Rest<>()
                .setData(noPropertyResponse)
                .setCode(errorCode.getCode())
                .setMsg(ServiceExceptionUtil.error(errorCode, null, params).getMessage())
                .setTip(errorCode.getTip())
                .setSuccess(false);
    }
    
    public static Rest<BaseResponse> error(ServiceException e) {
        return new Rest<>()
                .setData(noPropertyResponse)
                .setCode(e.getCode())
                .setMsg(e.getMessage())
                .setTip(e.getTip())
                .setSuccess(false);
    }

    private static final ConcurrentHashMap<String, Class<?>> loadedClass = new ConcurrentHashMap<>();

    /**
     * 向量函数，即 property 和 value 都是可以动态指定
     * @param property
     * @param value
     * @param valueClazz
     * @param <T> value 的 Class 对象
     * @return
     */
    public static <T> Rest<BaseResponse> vector(String property, Object value, Class<T> valueClazz) {
        String upperProperty = Character.toUpperCase(property.charAt(0)) + property.substring(1);
        String className = Rest.class.getPackage().getName() + ".Vector$" + upperProperty + "$" + valueClazz.getName().replace(".", "$");

        Object o;
        Class clazz;

        synchronized (loadedClass) {
            if (loadedClass.containsKey(className)) {
                clazz = loadedClass.get(className);
            } else {
                ClassPool pool = new ClassPool();
                ClassLoader classLoader = Rest.class.getClassLoader();
                pool.insertClassPath(new LoaderClassPath(classLoader));
                CtClass cc = pool.makeClass(className);
                try {
                    cc.setSuperclass(pool.get(BaseResponse.class.getName()));
                    CtClass valueCtClass = pool.get(valueClazz.getName());

                    CtField f = new CtField(valueCtClass, property, cc);
                    f.setModifiers(Modifier.PUBLIC);
                    cc.addField(f);

                    CtClass[] parameters = {valueCtClass};
                    CtMethod setter = new CtMethod(pool.get(void.class.getName()), "set" + upperProperty , parameters, cc);
                    setter.setBody("{this." + property + " = $1;}");
                    cc.addMethod(setter);

                    CtMethod getter = new CtMethod(valueCtClass, "get" + upperProperty, null, cc);
                    getter.setBody("{return this." + property + ";}");
                    cc.addMethod(getter);

                    clazz = cc.toClass();
                    loadedClass.put(className, clazz);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }

        try {
            o = clazz.newInstance();
            Method method = clazz.getDeclaredMethod("set" + upperProperty, valueClazz);
            method.invoke(o, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
        return Rest.success((BaseResponse) o);
    }
}
