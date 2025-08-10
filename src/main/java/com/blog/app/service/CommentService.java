package com.blog.app.service;

import com.blog.app.dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(Long postId, CommentDto commentDto);

    CommentDto getCommentById(Long postId, Long id);

    CommentDto updateCommentById(Long postId, Long commentId, CommentDto commentDto);

    String deleteCommentById(Long postId, Long id);

    List<CommentDto> getAllByPagintion(Long postId, int pageNo, int pageSize, String sortBy, String sortDir);

    List<CommentDto> getCommentsByPostId(Long postId);
}
