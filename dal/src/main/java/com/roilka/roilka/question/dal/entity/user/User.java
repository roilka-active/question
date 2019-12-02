package com.roilka.roilka.question.dal.entity.user;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName User
 * @Description TODO
 * @Author zhanghui1
 * @Date 2019/11/29 17:27
 **/
@Data
@Component("user")
public class User {
    @Value("1")
    private Long id;
    @Value("user_name_default")
    private String userName;
    @Value("note_default")
    private String note;
}
