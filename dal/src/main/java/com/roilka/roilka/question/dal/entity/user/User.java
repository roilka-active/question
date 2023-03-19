package com.roilka.roilka.question.dal.entity.user;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName User
 * @Description TODO
 * @Author changyou
 * @Date 2019/11/29 17:27
 **/
@Data
@Component("user")
public class User {
    @Value("1")
    private Long id;
    @Value("user_name_default")
    private String userName;
    @Value("user_code")
    private String userCode;
    @Value("note_default")
    private String note;
}
