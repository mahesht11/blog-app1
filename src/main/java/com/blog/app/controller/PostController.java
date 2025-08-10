package com.blog.app.controller;


import com.blog.app.dto.PostCommentDto;
import com.blog.app.dto.PostDto;
import com.blog.app.service.PostService;
import com.blog.app.utility.AppConstants;
import jakarta.validation.Valid;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class PostController {

    @Autowired
    public PostService postService;

    @GetMapping("test")
    public String check(){
        return "welcome to blog-app!";
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPosts(){
        log.info("PostController : getAllPosts :");
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.FOUND);
    }

    @PostMapping("/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        log.info("PostController : createPost : "+postDto.title());
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id){
        log.info("PostController : getPostById :"+id);
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.FOUND);
    }
    @PutMapping("/posts/{id}")
    public ResponseEntity<PostDto> updatePostById(@PathVariable Long id, @Valid @RequestBody PostDto postDto){
        log.info("PostController : updatePostById :"+id);
        return new ResponseEntity<>(postService.updatePostById(id, postDto), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable Long id){
        log.info("PostController :  deletePostById : "+id);
        return new ResponseEntity<>(postService.deletePostById(id), HttpStatus.OK);
    }

    @GetMapping("/posts/pagination")
    public ResponseEntity<List<PostDto>> getAllByPagination(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)int pageSize,
                                                            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir){
        log.info("PostController :  getAllByPagination : "+pageNo +"-"+pageSize );
        return new ResponseEntity<>(postService.getAllByPagintion(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/comments/all")
    public ResponseEntity<PostCommentDto> getCommentsWithPostId(@PathVariable Long postId){
        log.info("PostController :  getCommentsWithPostId : "+postId);
        return new ResponseEntity<>(postService.getCommentsWithPostId(postId), HttpStatus.OK);
    }
}
