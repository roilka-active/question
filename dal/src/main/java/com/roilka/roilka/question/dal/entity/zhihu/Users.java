package com.roilka.roilka.question.dal.entity.zhihu;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author changyou
 * @since 2019-12-06
 */
@TableName("users")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 默认id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String token;
    @TableField("index_url")
    private String indexUrl;
    private Integer isinit;
    @TableField("from_id")
    private Integer fromId;
    @TableField("from_token")
    private String fromToken;
    private Integer isparser;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }

    public Integer getIsinit() {
        return isinit;
    }

    public void setIsinit(Integer isinit) {
        this.isinit = isinit;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public String getFromToken() {
        return fromToken;
    }

    public void setFromToken(String fromToken) {
        this.fromToken = fromToken;
    }

    public Integer getIsparser() {
        return isparser;
    }

    public void setIsparser(Integer isparser) {
        this.isparser = isparser;
    }

    @Override
    public String toString() {
        return "Users{" +
        "id=" + id +
        ", token=" + token +
        ", indexUrl=" + indexUrl +
        ", isinit=" + isinit +
        ", fromId=" + fromId +
        ", fromToken=" + fromToken +
        ", isparser=" + isparser +
        "}";
    }
}
