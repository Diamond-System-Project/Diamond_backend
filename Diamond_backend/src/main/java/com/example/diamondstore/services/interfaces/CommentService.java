package com.example.diamondstore.services.interfaces;

import com.example.diamondstore.dto.CommentDTO;
import com.example.diamondstore.exceptions.DataNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {

    void addComment(CommentDTO comment) throws DataNotFoundException;

    void deleteComment(Integer commentId) throws DataNotFoundException;

    void editComment(Integer commentId, CommentDTO comment) throws DataNotFoundException;

//    List<Comment> getCommentByUserAndProductId(Integer userId, Integer pId);


}