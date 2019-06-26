package com.apocalypse.example.mapper.complex;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/5/31
 */
@Mapper
public interface ExampleUnionExampleExtendMapper {

    @Select(value = "select t1.name, t2.alias from example t1 left join example_extend t2 on t1.example_extend_id = t2.id where t1.id = #{id}")
    public Map<String, Object> unionSelectUseAnnotation(@Param("id") Long id);

    public Map<String, Object> unionSelectUseXml(Long id);
}
