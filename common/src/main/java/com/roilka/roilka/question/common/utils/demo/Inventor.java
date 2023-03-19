package com.roilka.roilka.question.common.utils.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Date;

/**
 * @program: roilka-question-parent
 * @description:
 * @author: zhanghui
 * @date: 2023-02-14 11:01
 **/
@Data
@NoArgsConstructor
public class Inventor {

    private String name;
    private String nationality;
    private String[] inventions;
    private Date birthdate;
    private PlaceOfBirth placeOfBirth;

    public Inventor(String nikola_tesla, Date time, String serbian) {
        this.name = nikola_tesla;
        this.birthdate = time;
        this.inventions = new String[]{serbian};
    }

    // 省略其它方法
    public void Inventor(String name,Date birthdate,String... inventions){
        this.name = name;
        this.birthdate = birthdate;
        this.inventions = inventions;
    }

}
