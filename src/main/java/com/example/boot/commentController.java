package com.example.boot;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@ToString
@Controller
@RequiredArgsConstructor
public class commentController {
    private final CommentService commentService;

    @PostMapping("/add-comment")
    String writePost(String content, Integer parentId, Authentication authentication) {
        commentService.saveItem(authentication.getName(), content, parentId);
        return "redirect:/list";
    }


}
