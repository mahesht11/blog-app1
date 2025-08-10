package com.blog.app.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public record ErrorDetails(Date timestamp, String message, String details) {
}
