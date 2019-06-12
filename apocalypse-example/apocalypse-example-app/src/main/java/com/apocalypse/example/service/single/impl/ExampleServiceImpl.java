package com.apocalypse.example.service.single.impl;

import cn.hutool.core.util.ObjectUtil;
import com.apocalypse.common.service.impl.BaseServiceImpl;
import com.apocalypse.example.convert.ExampleConvert;
import com.apocalypse.example.dto.ExampleCreateDTO;
import com.apocalypse.example.dto.ExampleModifyDTO;
import com.apocalypse.example.dto.ExampleQueryDTO;
import com.apocalypse.example.mapper.single.ExampleDOMapper;
import com.apocalypse.example.model.ExampleDO;
import com.apocalypse.example.service.single.ExampleService;
import com.apocalypse.example.vo.ExampleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/10
 */
@Slf4j
@Service
@CacheConfig(cacheNames = "spring-cache:example")
public class ExampleServiceImpl extends BaseServiceImpl<ExampleDO, Long> implements ExampleService {

    @Autowired
    private ExampleConvert exampleConvert;

    @Cacheable
    @Override
    public ExampleDO detail(Long id) {
        return selectByPrimaryKey(id);
    }

    @Override
    public List<ExampleVO> listQuery(ExampleQueryDTO exampleQueryDTO) {
        Weekend<ExampleDO> weekend = Weekend.of(ExampleDO.class);
        WeekendCriteria<ExampleDO, Object> weekendCriteria = weekend.weekendCriteria();

        String name = exampleQueryDTO.getName();
        LocalDate birthday = exampleQueryDTO.getBirthday();
        if (ObjectUtil.isNotNull(name)) {
            weekendCriteria.andLike(ExampleDO::getName, "%" + name + "%");
        }
        if (ObjectUtil.isNotNull(birthday)) {
            weekendCriteria.andEqualTo(ExampleDO::getBirthday, birthday);
        }
        List<ExampleDO> exampleDOList = selectByExample(weekend);
        List<ExampleVO> exampleVOList = new ArrayList<>(exampleDOList.size());
        exampleDOList.stream().forEach(exampleDO -> {
            ExampleVO exampleVO = exampleConvert.convert(exampleDO);
            exampleVOList.add(exampleVO);
        });
        return exampleVOList;
    }

    @CachePut(key = "#result.id")
    @Override
    public ExampleDO modify(ExampleModifyDTO exampleModifyDTO) {
        ExampleDO exampleDO = exampleConvert.convert(exampleModifyDTO);
        updateByPrimaryKeySelective(exampleDO);
        return exampleDO;
    }

    @CacheEvict
    @Override
    public void delete(Long id) {
        deleteByPrimaryKey(id);
    }

    @Override
    public ExampleDO create(ExampleCreateDTO exampleCreateDTO) {
        ExampleDO exampleDO = exampleConvert.convert(exampleCreateDTO);
        insertSelective(exampleDO);
        return exampleDO;
    }

}
