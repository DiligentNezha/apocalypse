package com.apocalypse.common.constraints;

import java.util.List;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/7/30
 */
public interface StringValuesReadable extends ValuesReadable{

    /**
     * 读取 values
     * @return
     */
    List<String> readStringValues();
}
