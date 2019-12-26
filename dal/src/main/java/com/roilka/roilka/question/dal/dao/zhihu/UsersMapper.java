package com.roilka.roilka.question.dal.dao.zhihu;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.roilka.roilka.question.dal.entity.zhihu.Users;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author changyou
 * @since 2019-12-06
 */
@Repository
public interface UsersMapper extends BaseMapper<Users> {

}
