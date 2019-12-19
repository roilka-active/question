package com.roilka.roilka.question.dal.dao.zhihu;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roilka.roilka.question.dal.entity.zhihu.Follower;
import org.apache.ibatis.annotations.Flush;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author changyou
 * @since 2019-12-06
 */
@Mapper
public interface FollowerMapper extends BaseMapper<Follower> {

}
