package com.roilka.roilka.question.domain.service.zhihu.impl;

import com.roilka.roilka.question.dal.dao.zhihu.UsersInfoMapper;
import com.roilka.roilka.question.dal.entity.zhihu.UsersInfo;
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
@Service(value = "usersInfoService")
public class UsersInfoService extends AbstractServiceImpl<UsersInfoMapper, UsersInfo> {

}
