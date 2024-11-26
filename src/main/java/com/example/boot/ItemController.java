package com.example.boot;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;
    private final MembersRepositry membersRepositry;
    private final ItemRepository itemRepository;
    private final S3Service s3Service;

    @GetMapping("/list")
    String List(Model model) {
        Page<Item> result =  itemRepository.findPageBy(PageRequest.of(0,5));
        model.addAttribute("item", result);
        model.addAttribute("pages", result.getTotalPages());
        var result1 = itemRepository.findById(29);
        model.addAttribute("item1", result1);
        return "list";
    }

    @GetMapping("/write")
    String write() {
        return "write";
    }

    @PostMapping("/add")
    String writePost(String title, int price, String img, Authentication auth) {
        itemService.saveItem(title, price, img, auth.getName());
        return "redirect:/list";
    }

    @GetMapping("/detail/{id}")
    String detail(@PathVariable Integer id, Model model) {
        System.out.println("detail 메서드 호출됨, id: " + id); // 첫 번째 로그
        Optional<Item> result = itemService.findItemById(id);

        if (result.isPresent()) {
            model.addAttribute("item", result.get());
            var comments = commentRepository.findAllByParentId(id);
            model.addAttribute("comments", comments);
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
    @ResponseBody
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

    @GetMapping("/list/page/{id}")
    String getListPage1(@PathVariable Integer id, Model model) {
        Page<Item> result =  itemRepository.findPageBy(PageRequest.of(id-1,5));
        model.addAttribute("item", result);
        model.addAttribute("pages", result.getTotalPages());
        return "list";
    }


    @GetMapping("/presigned-url")
    @ResponseBody
    String getURL(@RequestParam String filename){
        var result = s3Service.createPresignedUrl("oharin/" + filename);
        return result;
    }

    @PostMapping("/search")
    String postSearch(@RequestParam String searchText, Model model) {
        var result  =  itemRepository.fullTextSearch(searchText);
        model.addAttribute("item", result);
        model.addAttribute("pages", result.size());
        return "list";
    }
}
