package com.spring.filter.entity;

import com.spring.filter.annotation.ParamName;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体
 */
@Data
public class LoginInput implements Serializable {

    private String userName;

    private String password;

    @ParamName(name = "orgCode")
    private String listCode;

    private String viewCode;

}
