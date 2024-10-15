package com.example.boot;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@ToString
@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final PasswordEncoder passwordEncoder;

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

    @GetMapping("/register")
    String register() {
        return "register";
    }

    @PostMapping("/loginAdd")
    String loginAdd(String username, String password, String displayName) throws BadRequestException {
        if (itemService.isFindMemberByUsername(username)) throw new BadRequestException("아이디가 겹침");
        if (password.length()< 8) throw new BadRequestException("password가 8자리 미만");
        if (username.length()< 8) throw new BadRequestException("username이 8자리 미만");
        String encodePassword = passwordEncoder.encode(password);
        itemService.login(username, encodePassword, displayName);

        return "redirect:/list";
    }

    @GetMapping("/login")
    String login() {
        return "login.html";
    }
}
