package com.ws.hw1.controller.post.mapper;

import com.ws.hw1.controller.post.dto.PostDto;
import com.ws.hw1.model.Post;
import org.mapstruct.Mapper;

@Mapper
public interface PostMapper {
    PostDto toDTO(Post post);
}
