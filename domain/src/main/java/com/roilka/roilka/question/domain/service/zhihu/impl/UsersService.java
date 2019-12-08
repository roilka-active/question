package com.roilka.roilka.question.domain.service.zhihu.impl;

import com.roilka.roilka.question.dal.dao.zhihu.UsersMapper;
import com.roilka.roilka.question.dal.entity.zhihu.Users;
import com.roilka.roilka.question.domain.service.base.AbstractServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author changyou
 * @since 2019-12-06
 */
@Service(value = "userService")
public class UsersService extends AbstractServiceImpl<UsersMapper, Users> {

}
