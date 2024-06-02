package com.example.diamondstore.repositories;

import com.example.diamondstore.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository


public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
