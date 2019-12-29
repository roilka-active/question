package com.roilka.roilka.question.domain.service.zhihu.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.roilka.roilka.question.dal.dao.zhihu.HistoryTodayMapper;
import com.roilka.roilka.question.dal.entity.HistoryToday;
import com.roilka.roilka.question.domain.service.zhihu.IHistoryTodayService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 历史的今天 服务实现类
 * </p>
 *
 * @author changyou
 * @since 2019-12-30
 */
@Service
public class HistoryTodayService extends ServiceImpl<HistoryTodayMapper, HistoryToday> implements IHistoryTodayService {

}
