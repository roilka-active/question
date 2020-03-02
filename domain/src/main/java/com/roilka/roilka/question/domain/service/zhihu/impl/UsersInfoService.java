package com.roilka.roilka.question.domain.service.zhihu.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roilka.roilka.question.dal.dao.zhihu.UsersInfoMapper;
import com.roilka.roilka.question.dal.entity.zhihu.UsersInfo;
import com.roilka.roilka.question.domain.service.zhihu.IUsersInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
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
@Slf4j
@Service(value = "usersInfoService")
public class UsersInfoService extends ServiceImpl<UsersInfoMapper,UsersInfo> implements IUsersInfoService ,
        InitializingBean , DisposableBean {

    @Autowired
    private UsersInfoMapper usersInfoMapper;
    @Override
    public List<UsersInfo> getList() {
        return usersInfoMapper.getList();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
       log.info("UsersInfoService已经初始化了，你想干点啥？");
    }

    @Override
    public void destroy() throws Exception {
        log.info("UsersInfoService正在销毁，你有什么可留念的吗？");
    }
}
