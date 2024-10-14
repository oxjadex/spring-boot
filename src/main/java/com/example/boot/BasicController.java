package com.example.boot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class BasicController {
    @GetMapping("/")
    @ResponseBody
    String hello() {
        return "안녕하세요";
    }

    @GetMapping("/about")
    @ResponseBody
    String fishing() {
        return "피싱사이트에요";
    }

    @GetMapping("/html")
    @ResponseBody
    String nice() {
        return "<h4>반갑습니다</h4>";
    }

    @GetMapping("/html1")
    String mainPage() {
        return "index.html";
    }

    @GetMapping("/date")
    @ResponseBody
    Date getDate() {
        Date d = new Date();
        return d;
    }
}
