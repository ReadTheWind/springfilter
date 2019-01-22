package com.spring.filter.controller;

import com.spring.filter.annotation.ModuleParams;
import com.spring.filter.entity.LoginInput;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/filter")
public class FilterController {

    @ModuleParams(moduleNames = "111")
    @PostMapping("/login")
    public String login(@RequestBody LoginInput loginInput) {
        if (StringUtils.isEmpty(loginInput.getListCode())) {
            return "hello word";
        }
        return "无权限";
    }
}
