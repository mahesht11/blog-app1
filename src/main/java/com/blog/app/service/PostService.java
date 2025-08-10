package com.blog.app.service;

import com.blog.app.dto.PostCommentDto;
import com.blog.app.dto.PostDto;

import java.util.List;

public interface PostService {
    List<PostDto> getAllPosts();

    PostDto createPost(PostDto postDto);

    PostDto getPostById(Long id);

    PostDto updatePostById(Long id, PostDto postDto);

    String deletePostById(Long id);

    List<PostDto> getAllByPagintion(int pageNo, int pageSize, String sortBy, String sortDir);

    PostCommentDto getCommentsWithPostId(Long postId);
}
