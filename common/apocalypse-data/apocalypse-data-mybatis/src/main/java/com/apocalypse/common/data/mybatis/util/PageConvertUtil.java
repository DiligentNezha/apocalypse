package com.apocalypse.common.data.mybatis.util;


import com.github.pagehelper.PageInfo;
import com.apocalypse.common.core.api.Page;

import java.util.List;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2020/3/30
 */
public class PageConvertUtil {

    /**
     * PageHelper的PageInfo转成自定义的Page对象
     * @param values
     * @return
     */
    public static Page convert(List<?> values)  {
        if(values==null){
            return new Page();
        }
        PageInfo<?> pageInfo = new PageInfo<>(values);
        return new Page()
                .setCurrent(pageInfo.getPageNum())
                .setSize(pageInfo.getPageSize())
                .setTotal(pageInfo.getTotal());
    }
}
