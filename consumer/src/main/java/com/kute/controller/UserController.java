package com.kute.controller;

import com.alibaba.fastjson.JSONObject;
import com.kute.domain.User;
import com.kute.service.IConsumerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * created by kute on 2018/05/14 23:13
 */
@Controller
@RequestMapping("/api/v1/user")
public class UserController {

    @Resource
    private IConsumerService consumerService;

    @ResponseBody
    @RequestMapping(value = "/get/{userId}", method = RequestMethod.GET, produces = "application/json")
    public String getUser(@PathVariable("userId") Integer userId) {
        User user = consumerService.getUser(userId);
        return JSONObject.toJSONString(user);
    }

}
