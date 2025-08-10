package com.blog.app.dto;

import java.util.List;

public record PostCommentDto(Long id, String title, String description, String content, List<CommentDto> commentDtoList) {
}
