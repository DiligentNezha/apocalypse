package com.apocalypse.common.core.constraints;

import java.util.List;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2020/3/30
 */
public interface StringValuesReadable extends ValuesReadable{

    /**
     * 读取 values
     * @return
     */
    List<String> readStringValues();
}
