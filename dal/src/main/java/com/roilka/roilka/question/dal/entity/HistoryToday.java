package com.roilka.roilka.question.dal.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 历史的今天
 * </p>
 *
 * @author changyou
 * @since 2019-12-30
 */
@TableName("history_today")
public class HistoryToday implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 年
     */
    private Integer year;
    /**
     * 月
     */
    private Integer month;
    /**
     * 天
     */
    private Integer day;
    /**
     * 类型：1：国外，2：国内
     */
    private Integer type;
    /**
     * 标题
     */
    private String title;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "HistoryToday{" +
        "id=" + id +
        ", year=" + year +
        ", month=" + month +
        ", day=" + day +
        ", type=" + type +
        ", title=" + title +
        "}";
    }
}
