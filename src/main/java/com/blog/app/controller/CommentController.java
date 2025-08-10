package com.blog.app.controller;

import com.blog.app.dto.CommentDto;
import com.blog.app.dto.PostDto;
import com.blog.app.service.CommentService;
import com.blog.app.utility.AppConstants;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/comments/test")
    public String check(){
        return "welcome to comments in blog-app!";
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable(value = "postId") Long postId){
        log.info("CommentController : getAllComments :");
        return new ResponseEntity<>(commentService.getCommentsByPostId(postId), HttpStatus.FOUND);
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") Long postId, @Valid @RequestBody CommentDto commentDto){
        log.info("CommentController : createComment : ");
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") Long postId, @PathVariable Long commentId){
        log.info("CommentController : getCommentById :"+commentId);
        return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.FOUND);
    }
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable(value = "postId") Long postId, @PathVariable Long commentId,@RequestBody CommentDto commentDto){
        log.info("CommentController : updateCommentById :"+postId);
        return new ResponseEntity<>(commentService.updateCommentById(postId, commentId, commentDto), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentById(@PathVariable(value = "postId") Long postId, @PathVariable Long commentId){
        log.info("CommentController :  deleteCommentById : "+commentId);
        return new ResponseEntity<>(commentService.deleteCommentById(postId, commentId), HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/comments/pagination")
    public ResponseEntity<List<CommentDto>> getAllByPagination(@PathVariable(value = "postId") Long postId,
                                                               @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                               @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)int pageSize,
                                                               @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                               @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir){
        log.info("CommentController :  getAllByPagination : pageNo : "+pageNo +", PageSize : "+pageSize, sortBy, sortDir );
        return new ResponseEntity<>(commentService.getAllByPagintion(postId, pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

}
