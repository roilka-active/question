package com.roilka.roilka.question.domain.service.zhihu.impl;

import com.roilka.roilka.question.dal.dao.zhihu.FollowerMapper;
import com.roilka.roilka.question.dal.entity.zhihu.Follower;
import com.roilka.roilka.question.domain.service.base.AbstractServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author changyou
 * @since 2019-12-06
 */
@Service(value = "followerService")
@Primary
public class FollowerService extends AbstractServiceImpl<FollowerMapper, Follower>  {

}
