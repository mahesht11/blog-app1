package com.blog.app.service;

import com.blog.app.dto.CommentDto;
import com.blog.app.dto.PostCommentDto;
import com.blog.app.dto.PostDto;
import com.blog.app.entity.Post;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.repository.CommentRepository;
import com.blog.app.repository.PostRepository;
import lombok.Builder;
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
@Builder
public class PostServiceImpl implements PostService{

    @Autowired
    public PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<PostDto> getAllPosts() {
        log.info("PostServiceImpl : getAllPosts() :");
        return postRepository.findAll().stream().map(post -> new PostDto(post.getId(), post.getTitle(), post.getDescription(), post.getContent())).collect(Collectors.toList());
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        log.info("PostServiceImpl : createPost() :");
        Post post = Post.builder().title(postDto.title())
                .description(postDto.description())
                .content(postDto.content())
                .build();
        Post post1 = postRepository.saveAndFlush(post);
        return new PostDto(post1.getId(),post1.getTitle(), post1.getDescription(), post1.getContent());
    }

    @Override
    public PostDto getPostById(Long id) {
        log.info("PostServiceImpl : getPostById() : Post ID: "+id);
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, "Id", "Resource Not found with thid id : "+id));
        return new PostDto(post.getId(), post.getTitle(), post.getDescription(), post.getContent());
    }

    @Override
    public PostDto updatePostById(Long id, PostDto postDto) {
        log.info("PostServiceImpl : updatePostById() : PostID : "+id);
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, "Id", "Resource Not found with thid id : "+id));
        post.setTitle(postDto.title()!=null?postDto.title():post.getTitle());
        post.setDescription(postDto.description()!=null ? postDto.description(): post.getDescription());
        post.setContent(postDto.content()!=null? postDto.content():post.getContent());
        Post post1 = postRepository.saveAndFlush(post);
        return new PostDto(post1.getId(),post1.getTitle(), post1.getDescription(), post1.getContent());
    }

    @Override
    public String deletePostById(Long id) {
        log.info("PostServiceImpl : deletePostById() : postId :"+id);
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id, "POST-Id", "Resource Not found with thid id : "+id));
        postRepository.deleteById(post.getId());
        return "Post deleted with this id :"+post.getId();
    }

    @Override
    public List<PostDto> getAllByPagintion(int pageNo, int pageSize, String sortBy, String sortDir) {
        log.info("PostServiceImpl : getAllByPagintion() : pageNo : "+pageNo+", pageSize: "+pageSize);
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
       return postRepository.findAll(pageable).getContent().stream().map(post -> new PostDto(post.getId(), post.getTitle(), post.getDescription(), post.getContent())).collect(Collectors.toList());
    }

    @Override
    public PostCommentDto getCommentsWithPostId(Long postId) {
        log.info("PostServiceImpl : getCommentsWithPostId :"+postId);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(postId, "Id", "Resource Not found with thid id : "+postId));
       List<CommentDto> commentDtoList =  commentRepository.findAllByPostId(postId, null).stream().map(comment -> new CommentDto(comment.getId(), comment.getName(), comment.getEmail(),comment.getBody())).collect(Collectors.toList());
        return new PostCommentDto(post.getId(),post.getTitle(),post.getDescription(),post.getContent(), commentDtoList);
    }
}
