package com.apocalypse.example.convert;

import com.apocalypse.example.dto.ExampleCreateDTO;
import com.apocalypse.example.dto.ExampleModifyDTO;
import com.apocalypse.example.model.ExampleDO;
import com.apocalypse.example.vo.ExampleVO;
import org.mapstruct.Mapper;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/11
 */
@Mapper(componentModel = "spring")
public interface ExampleConvert {

    ExampleDO convert(ExampleCreateDTO exampleCreateDTO);

    ExampleDO convert(ExampleModifyDTO exampleModifyDTO);

    ExampleVO convert(ExampleDO exampleDO);
}
