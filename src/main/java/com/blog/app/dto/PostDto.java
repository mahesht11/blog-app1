package com.blog.app.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record PostDto(Long id,
                      @NotEmpty @Size(min = 2, message = "Post title should have at least 2 characters") String title,
                      @NotEmpty @Size(min = 10, message = "Post description should have at least 10 characters") String description,
                      @NotEmpty String content) {
}
