package com.example.boot;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@ToString
public class Item2Controller {
    private final Item2Repository item2Repository;

    @GetMapping("/item2")
    String item2(Model model) {
        List<Item2> result = item2Repository.findAll();
        System.out.println(result);
        model.addAttribute("item2", result);
        System.out.println(result);
        return "list2.html";
    }
}
