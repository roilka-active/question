package com.roilka.roilka.question.dal.entity.zhihu;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author changyou
 * @since 2019-12-06
 */
@TableName("users_info")
public class UsersInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    /**
     * 地址
     */
    private String address;
    /**
     * 教育
     */
    private String education;
    /**
     * 公司
     */
    private String company;
    /**
     * 工作
     */
    private String job;
    /**
     * 签名
     */
    private String headline;
    /**
     * pk主表引用
     */
    @TableField("user_id")
    private String userId;
    private String answer;
    private String question;
    /**
     * 文章总数
     */
    private String article;
    /**
     * 收藏总数
     */
    private String favorite;
    private String agree;
    private String thanked;
    private String following;
    private String followers;
    private String topic;
    private String columns;
    private String sex;
    private Date updatetime;
    private String weibo;
    private String token;
    @TableField("index_url")
    private String indexUrl;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }

    public String getThanked() {
        return thanked;
    }

    public void setThanked(String thanked) {
        this.thanked = thanked;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
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

    @Override
    public String toString() {
        return "UsersInfo{" +
        "name=" + name +
        ", address=" + address +
        ", education=" + education +
        ", company=" + company +
        ", job=" + job +
        ", headline=" + headline +
        ", userId=" + userId +
        ", answer=" + answer +
        ", question=" + question +
        ", article=" + article +
        ", favorite=" + favorite +
        ", agree=" + agree +
        ", thanked=" + thanked +
        ", following=" + following +
        ", followers=" + followers +
        ", topic=" + topic +
        ", columns=" + columns +
        ", sex=" + sex +
        ", updatetime=" + updatetime +
        ", weibo=" + weibo +
        ", token=" + token +
        ", indexUrl=" + indexUrl +
        "}";
    }
}
