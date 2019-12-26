package com.roilka.roilka.question.dal.dao.zhihu;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roilka.roilka.question.dal.entity.zhihu.Area;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author changyou
 * @since 2019-12-22
 */
public interface AreaMapper extends BaseMapper<Area> {

    int insertSelective(Area area);
}
