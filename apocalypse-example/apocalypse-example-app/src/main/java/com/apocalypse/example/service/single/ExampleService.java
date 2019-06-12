package com.apocalypse.example.service.single;

import com.apocalypse.common.service.BaseService;
import com.apocalypse.example.dto.ExampleCreateDTO;
import com.apocalypse.example.dto.ExampleModifyDTO;
import com.apocalypse.example.dto.ExampleQueryDTO;
import com.apocalypse.example.model.ExampleDO;
import com.apocalypse.example.vo.ExampleVO;

import java.util.List;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/10
 */
public interface ExampleService extends BaseService<ExampleDO, Long> {

    ExampleDO detail(Long id);

    List<ExampleVO> listQuery(ExampleQueryDTO exampleQueryDTO);

    ExampleDO modify(ExampleModifyDTO exampleModifyDTO);

    void delete(Long id);

    ExampleDO create(ExampleCreateDTO exampleCreateDTO);
}
