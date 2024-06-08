package com.example.diamondstore.repositories;

import com.example.diamondstore.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByProductId(Integer productId);
    List<Comment> findAllByUserId(Integer userId);
    List<Comment> findAllByUserIdAndProductId(Integer userId, Integer productId);
}
