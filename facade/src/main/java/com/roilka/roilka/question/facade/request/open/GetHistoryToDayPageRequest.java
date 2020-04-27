package com.roilka.roilka.question.facade.request.open;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName GetHistoryToDayPageRequest
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/4/27 18:02
 **/
@Data
@ApiModel(description = "分页查询历史的今天请求实体")
public class GetHistoryToDayPageRequest implements Serializable {

    @ApiModelProperty(value = "主键")
    private Integer id;
    /**
     * 年
     */
    @ApiModelProperty(value = "年")
    private Integer year;
    /**
     * 月
     */
    @ApiModelProperty(value = "月")
    private Integer month;
    /**
     * 天
     */
    @ApiModelProperty(value = "天")
    private Integer day;
    /**
     * 类型：1：国外，2：国内
     */
    @ApiModelProperty(value = "类型：1：国外，2：国内")
    private Integer type;

    @ApiModelProperty(value = "当前页")
    private Integer pageNum;

    @ApiModelProperty(value = "当前页显示数量")
    private Integer pageSize;
}
