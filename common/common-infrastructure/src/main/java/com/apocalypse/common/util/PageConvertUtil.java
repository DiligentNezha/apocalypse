package com.apocalypse.common.util;

import com.apocalypse.common.dto.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/5/23
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
                .setPageNum(pageInfo.getPageNum())
                .setPageSize(pageInfo.getPageSize())
                .setTotal(pageInfo.getTotal());
    }
}
