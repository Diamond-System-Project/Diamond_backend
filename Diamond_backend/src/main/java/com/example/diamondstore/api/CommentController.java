package com.example.diamondstore.api;

import com.example.diamondstore.dto.CommentDTO;
import com.example.diamondstore.exceptions.DataNotFoundException;
import com.example.diamondstore.services.interfaces.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PutMapping("/{commentId}")
    public ResponseEntity<?> editComment(
            @PathVariable int commentId,
            @Valid @RequestBody CommentDTO comment,
            Authentication authentication
    ) {
        try {
            commentService.editComment(commentId, comment);
            return ResponseEntity.ok("Update comment successfully");
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @PostMapping("")
        public ResponseEntity<?> addComment(
            @Valid @RequestBody CommentDTO comment,
            Authentication authentication
    ) {
        try {
            // Kiểm tra người dùng đã đăng nhập
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }

            commentService.addComment(comment);
            return ResponseEntity.ok("Add successfully");
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable int commentId,
            Authentication authentication
    ) {
        try {
            // Kiểm tra người dùng đã đăng nhập
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }

            commentService.deleteComment(commentId);
            return ResponseEntity.ok("Delete comment successfully");
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }
}
