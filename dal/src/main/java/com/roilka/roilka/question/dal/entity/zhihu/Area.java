package com.roilka.roilka.question.dal.entity.zhihu;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author changyou
 * @since 2019-12-22
 */
@Data
@TableName("area")
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.INPUT)
    private Integer id;
    private String name;
    @TableField("parent_id")
    private Integer parentId;
    @TableField("parent_name")
    private Integer parentName;
    @TableField("area_code")
    private String areaCode;
    @TableField("zip_code")
    private String zipCode;
    private Integer depth;



}
