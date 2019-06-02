package com.apocalypse.common.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @author <a href="jingkaihui@adpanshi.com">jingkaihui</a>
 * @Description JSONObject工具
 * @date 2019/5/6
 */
public class JSONObjectUtil {

    /**
     * 任意对象转换成JSONObject对象
     * @return
     */
    public static JSONObject convert(Object o) {
        return JSONObject.parseObject(JSONObject.toJSONString(o));
    }

}
