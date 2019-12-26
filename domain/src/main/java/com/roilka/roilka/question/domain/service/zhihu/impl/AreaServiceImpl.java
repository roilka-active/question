package com.roilka.roilka.question.domain.service.zhihu.impl;

import com.roilka.roilka.question.dal.dao.zhihu.AreaMapper;
import com.roilka.roilka.question.dal.entity.zhihu.Area;
import com.roilka.roilka.question.domain.service.zhihu.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName AreaServiceImpl
 * @Description TODO
 * @Author zhanghui1
 * @Date 2019/12/26 10:27
 **/
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaMapper areaMapper;
    @Override
    public Boolean addArea(Area area) {

        return areaMapper.insertSelective(area) > 0;
    }
}
