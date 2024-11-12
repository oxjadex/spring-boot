package com.example.boot;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    private final MembersRepositry membersRepositry;

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
    String writePost(String title, int price, Authentication auth) {
        itemService.saveItem(title, price, auth.getName());
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


    @PostMapping("/loginAdd")
    String loginAdd(String username, String password, String displayName) throws BadRequestException {
        if (itemService.isFindMemberByUsername(username)) throw new BadRequestException("아이디가 겹침");
        if (password.length()< 8) throw new BadRequestException("password가 8자리 미만");
        if (username.length()< 8) throw new BadRequestException("username이 8자리 미만");
        String encodePassword = passwordEncoder.encode(password);
        itemService.login(username, encodePassword, displayName);
        System.out.println("회언가입 성공");
        return "redirect:/list";
    }

    @GetMapping("/login")
    String login() {
        return "login.html";
    }

    @GetMapping("/signup")
    public String signup(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            return "redirect:/list";
        }
        return "signup.html";
    }

    @GetMapping("/my-page")
    public String myPage(Authentication auth) {
        if(auth!=null) {
            CustomUser user = (CustomUser) auth.getPrincipal();
            System.out.println(user.displayName);
            System.out.println(user.id);
            return "mypage";
        }
        return "redirect:/list";
    }

    @GetMapping("/register")
    public String register(Authentication auth) {
        if(auth!=null){
            return "redirect:/list";
        }
        return "register";
    }

    @GetMapping("/user/1")
    public MemberDTO user() {
        Optional<Members> a= membersRepositry.findById(1);
        var result = a.get();
        var data = new MemberDTO(result.getUsername(), result.getDisplayName());
        return data;
    }

    class MemberDTO {
        public String username;
        public String displayName;

        MemberDTO(String username, String displayName) {
            this.username = username;
            this.displayName = displayName;
        }
    }
}
