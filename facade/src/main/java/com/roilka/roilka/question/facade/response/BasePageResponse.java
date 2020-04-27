package com.roilka.roilka.question.facade.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName BasePageResponse
 * @Description TODO
 * @Author zhanghui1
 * @Date 2020/4/27 17:55
 **/
@Data
@ApiModel(description = "基础分页返回信息实体")
public class BasePageResponse<T> implements Serializable {

    @ApiModelProperty(value = "总数")
    private Long total;

    @ApiModelProperty(value = "结果列表")
    private List<T> resultList;
}
