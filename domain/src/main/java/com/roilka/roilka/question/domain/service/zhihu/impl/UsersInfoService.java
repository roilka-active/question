package com.roilka.roilka.question.domain.service.zhihu.impl;

import com.roilka.roilka.question.dal.dao.zhihu.UsersInfoMapper;
import com.roilka.roilka.question.dal.entity.zhihu.UsersInfo;
import com.roilka.roilka.question.domain.service.base.AbstractServiceImpl;
import com.roilka.roilka.question.domain.service.zhihu.IUsersInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author changyou
 * @since 2019-12-06
 */
@Service(value = "usersInfoService")
public class UsersInfoService implements IUsersInfoService {

    @Autowired
    private UsersInfoMapper usersInfoMapper;
    @Override
    public List<UsersInfo> getList() {
        return usersInfoMapper.getList();
    }
}
