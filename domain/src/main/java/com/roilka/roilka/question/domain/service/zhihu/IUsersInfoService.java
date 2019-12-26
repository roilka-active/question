package com.roilka.roilka.question.domain.service.zhihu;

import com.baomidou.mybatisplus.extension.service.IService;
import com.roilka.roilka.question.dal.entity.zhihu.UsersInfo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author changyou
 * @since 2019-12-06
 */
public interface IUsersInfoService extends IService<UsersInfo>{
    List<UsersInfo> getList();
}
