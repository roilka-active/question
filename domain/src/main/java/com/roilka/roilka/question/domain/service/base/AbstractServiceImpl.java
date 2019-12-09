package com.roilka.roilka.question.domain.service.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author zhanghui
 * @description 公共抽象服务实现类
 * @date 2019/12/9
 */
public abstract class AbstractServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl {
}
