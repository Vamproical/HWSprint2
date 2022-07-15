package com.ws.hw1.service.post;

import com.ws.hw1.controller.post.dto.UpdatePostDto;
import com.ws.hw1.exceptionhandler.exception.NotFoundException;
import com.ws.hw1.model.Post;
import com.ws.hw1.repository.PostRepository;
import com.ws.hw1.service.argument.CreatePostArgument;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public Post getExisting(@NonNull UUID id) {
        return postRepository.findById(id).orElseThrow(() -> new NotFoundException("The post not found"));
    }

    @Override
    public Post create(@NonNull CreatePostArgument argumentPost) {
        Post post = Post.builder()
                        .name(argumentPost.getName())
                        .build();

        return postRepository.save(post);
    }

    @Override
    public Post update(@NonNull UUID id, @NonNull UpdatePostDto argumentPost) {
        Post post = getExisting(id);
        post.setName(argumentPost.getName());

        return postRepository.save(post);
    }

    @Override
    public void delete(@NonNull UUID id) {
        getExisting(id);
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> getAll() {
        return postRepository.findAll();
    }

}
