package com.hyun.bookmarkshare.manage.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ManageController {

    // react apply, deprecate soon
    @GetMapping("/manage/my")
    public String goManagePage(){
        return "/manage/manage.html";
    }
}
