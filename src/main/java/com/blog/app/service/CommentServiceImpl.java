package com.blog.app.service;

import com.blog.app.dto.CommentDto;
import com.blog.app.entity.Comment;
import com.blog.app.entity.Post;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.repository.CommentRepository;
import com.blog.app.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        log.info("CommentServiceImpl : getAllComments() : postID : "+postId);
        return commentRepository.findAllByPostId(postId, null).stream().map(comment -> new CommentDto(comment.getId(), comment.getName(), comment.getEmail(),comment.getBody())).collect(Collectors.toList());
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        log.info("CommentServiceImpl : createComment: with postId :  "+postId);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(postId, "POST-ID", "Resource Not found with thid id : "+postId));
        Comment comment = Comment.builder()
                .name(commentDto.name())
                .body(commentDto.body())
                .email(commentDto.email())
                .post(post)
                .build();
        Comment comment1 = commentRepository.saveAndFlush(comment);
        return new CommentDto(comment1.getId(), comment1.getName(),comment1.getEmail(),comment1.getBody());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long id) {
        log.info("CommentServiceImpl : getCommentById: with commentID : "+id+" , PostId : "+postId);
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(postId, "ID", "Resource Not found with thid id : "+id));
        return new CommentDto(comment.getId(), comment.getName(),comment.getEmail(),comment.getBody());
    }

    @Override
    public CommentDto updateCommentById(Long postId, Long commentId, CommentDto commentDto) {
        log.info("CommentServiceImpl : updateCommentById: with commentID : "+commentId+" , PostId : "+postId);
        Post post  = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(postId, "POST-Id", "Resource Not found with thid id : "+postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException(commentId, "Comment-ID", "Resource Not found with thid id : "+commentId));
        comment.setName(commentDto.name()!=null?commentDto.name():comment.getName());
        comment.setBody(commentDto.body()!=null?commentDto.body():comment.getBody());
        comment.setEmail(commentDto.email()!=null?commentDto.email():comment.getEmail());
        Comment comment1 = commentRepository.saveAndFlush(comment);
        return new CommentDto(comment1.getId(), comment1.getName(),comment1.getEmail(),comment1.getBody());
    }

    @Override
    public String deleteCommentById(Long postId, Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(postId, "Comment -ID", "Resource Not found with thid id : "+id));
        commentRepository.deleteById(comment.getId());
        return "Successfully deleted the comment :"+id +" with the post id :"+postId ;
    }

    @Override
    public List<CommentDto> getAllByPagintion(Long postId, int pageNo, int pageSize, String sortBy, String sortDir) {
        org.springframework.data.domain.Sort sort = sortDir.equalsIgnoreCase(org.springframework.data.domain.Sort.Direction.ASC.name())? org.springframework.data.domain.Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return commentRepository.findAllByPostId(postId, pageable).stream().map(comment -> new CommentDto(comment.getId(), comment.getName(), comment.getEmail(),comment.getBody())).collect(Collectors.toList());
    }
}
