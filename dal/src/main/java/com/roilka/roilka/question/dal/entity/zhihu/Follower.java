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
 * @since 2019-12-06
 */
@TableName("follower")
public class Follower implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("user_token")
    private String userToken;
    @TableField("user_token_follower")
    private String userTokenFollower;


    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserTokenFollower() {
        return userTokenFollower;
    }

    public void setUserTokenFollower(String userTokenFollower) {
        this.userTokenFollower = userTokenFollower;
    }

    @Override
    public String toString() {
        return "Follower{" +
        "userToken=" + userToken +
        ", userTokenFollower=" + userTokenFollower +
        "}";
    }
}
