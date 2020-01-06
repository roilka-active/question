package com.roilka.roilka.question.domain.service.zhihu;

import com.roilka.roilka.question.dal.entity.zhihu.Area;

import java.util.Set;

/**
 * @ClassName AreaService
 * @Description TODO
 * @Author zhanghui1
 * @Date 2019/12/26 10:26
 **/
public interface AreaService {
    Boolean addArea(Area area);

    Boolean addAreaAsync( String province);
}
