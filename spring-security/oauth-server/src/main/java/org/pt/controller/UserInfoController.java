package org.pt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName UserInfoController
 * @Author pt
 * @Description
 * @Date 2025/3/3 14:07
 **/
@Controller
public class UserInfoController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
