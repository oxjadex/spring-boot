package com.example.boot;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ToString
@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/list")
    String List(Model model) {
        List<Item> result = itemService.findAllItem();
        model.addAttribute("item", result);
        System.out.println(result);
        return "list";
    }

    @GetMapping("/write")
    String write() {
        return "write";
    }

    @PostMapping("/add")
    String writePost(String title, Integer price) {
        itemService.saveItem(title, price);
        return "redirect:/list";
    }

    @GetMapping("/detail/{id}")
    String detail(@PathVariable Integer id, Model model) {
        Optional<Item> result = itemService.findItemById(id);
        if (result.isPresent()) {
            model.addAttribute("item", result.get());
            return "detail";
        }
        return "redirect:/list";
    }

    @GetMapping("/edit/{id}")
    String edit(@PathVariable Integer id, Model model) {
        Optional<Item> result = itemService.findItemById(id);
        if (result.isPresent()) {
            model.addAttribute("data", result.get());
            return "edit";
        } else {
            return "redirect:/list";
        }
    }

    @PostMapping("/edit")
    String editItem(@RequestParam Integer id, @RequestParam String title, @RequestParam Integer price) {
        itemService.editItem(id, title, price);
        return "redirect:/list";
    }

    @DeleteMapping("/delete")
    String deleteItem(@RequestParam Integer id) {
        itemService.deleteItemById(id);
        return "redirect:/list";
    }

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @PostMapping("/loginAdd")
    String loginAdd(String username, String password, String displayName) {
        var encoder = new BCryptPasswordEncoder();
        String encodePassword = encoder.encode(password);
        itemService.login(username, encodePassword, displayName);

        return "redirect:/list";
    }
}
