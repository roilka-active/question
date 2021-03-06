package com.roilka.roilka.question.dal.dao.zhihu;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roilka.roilka.question.dal.entity.zhihu.UsersInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author changyou
 * @since 2019-12-06
 */
@Mapper
public interface UsersInfoMapper extends BaseMapper<UsersInfo>{

    List<UsersInfo> getList();
}
