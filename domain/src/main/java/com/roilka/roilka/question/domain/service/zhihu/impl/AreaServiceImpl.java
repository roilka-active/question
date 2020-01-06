package com.roilka.roilka.question.domain.service.zhihu.impl;

import com.roilka.roilka.question.common.constant.RedisFix;
import com.roilka.roilka.question.common.utils.RedisUtils;
import com.roilka.roilka.question.dal.dao.zhihu.AreaMapper;
import com.roilka.roilka.question.dal.entity.zhihu.Area;
import com.roilka.roilka.question.domain.service.zhihu.AreaService;
import com.roilka.roilka.question.facade.response.zhihu.GetAreaDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName AreaServiceImpl
 * @Description TODO
 * @Author zhanghui1
 * @Date 2019/12/26 10:27
 **/
@Slf4j
@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaMapper areaMapper;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Boolean addArea(Area area) {

        return areaMapper.insert(area) > 0;
    }

    @Async
    @Override
    public Boolean addAreaAsync( String province) {
        Set<Area> list = new HashSet<>();
        log.info("build province,{}", province);
        Long count = redisUtils.redisHashSize(RedisFix.AREA + province);
        if (count == 0) {
            return false;
        }

        // 存入所有省份
        buildArea(RedisFix.AREA, province, list);
        Set<String> citySet = redisUtils.redisHashKeys(RedisFix.AREA + province);
        int cityCount = citySet.size();
        for (String city : citySet) {
            log.info("build city,name ={},cityCount={}", city, cityCount);
            cityCount--;
            count = redisUtils.redisHashSize(RedisFix.AREA + province + ":" + city);
            if (count == 0) {
                continue;
            }
            // 存入城市
            buildArea(RedisFix.AREA + province, city, list);
            Set<String> townSet = redisUtils.redisHashKeys(RedisFix.AREA + province + ":" + city);
            int townCount = townSet.size();
            for (String town : townSet) {
                // 存入乡镇
                //log.info("build town,id ={},cityCount = {},townCount={}", town, cityCount, townCount);
                townCount--;
                buildArea(RedisFix.AREA + province + ":" + city, town, list);
            }
        }
        log.info("开始入库，size={},list={}", list.size(),list);
        list.stream().forEach(record -> areaMapper.insert(record));
        return true;
    }


    private void buildArea(String key, String hashKey, Set<Area> list) {
        GetAreaDataResponse.ResultBean bean = redisUtils.redisHashGetWithInstance(key, hashKey, GetAreaDataResponse.ResultBean.class);
        Area area = new Area();
        area.setAreaCode(bean.getAreacode());
        area.setDepth(bean.getDepth());
        area.setId(bean.getId());
        area.setName(bean.getName());
        area.setParentId(bean.getParentid());
        area.setZipCode(bean.getZipcode());
        list.add(area);
    }
}
