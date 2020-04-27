package com.roilka.roilka.question.facade.response.open;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName HistoryToDayResponse
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/4/27 17:52
 **/
@Data
@ApiModel(description = "历史的今天返回信息实体")
public class HistoryToDayResponse {

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
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;
}
