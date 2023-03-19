/**
 *
 */
package com.roilka.roilka.question.domain.component.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

/**
 *  基础表字段
 */
@Data
public class BaseBean {

	/**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 创建用户code
     */
    @TableField("create_user_code")
    private String createUserCode;

    /**
     * 创建用户姓名
     */
    @TableField("create_user_name")
    private String createUserName;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 修改用户code
     */
    @TableField("update_user_code")
    private String updateUserCode;

    /**
     * 修改用户姓名
     */
    @TableField("update_user_name")
    private String updateUserName;


}
