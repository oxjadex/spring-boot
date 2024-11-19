package com.example.boot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void saveItem(String username, String content, Integer parentId) {
        comment comment = new comment();
        comment.setUsername(username);
        comment.setContent(content);
        comment.setParentId(parentId);
        commentRepository.save(comment);
    }

    public List<comment> findByParentId(Integer parentId) {
        System.out.println("findByParentId 호출됨, parentId: " + parentId);
        List<comment> comments = commentRepository.findAllByParentId(parentId);
        System.out.println("DB에서 가져온 댓글: " + comments);
        return comments;
    }
}
