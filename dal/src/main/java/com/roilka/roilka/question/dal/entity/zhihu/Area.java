package com.roilka.roilka.question.dal.entity.zhihu;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author changyou
 * @since 2019-12-22
 */
@TableName("area")
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    @TableField("parent_id")
    private Integer parentId;
    @TableField("area_code")
    private String areaCode;
    @TableField("zip_code")
    private String zipCode;
    private Integer depth;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    @Override
    public String toString() {
        return "Area{" +
        "id=" + id +
        ", name=" + name +
        ", parentId=" + parentId +
        ", areaCode=" + areaCode +
        ", zipCode=" + zipCode +
        ", depth=" + depth +
        "}";
    }
}
