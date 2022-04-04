package com.bookshop.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/test/hello")
    public String hello(){
        return "hello";
    }
}
