package com.ws.hw1.mapper;

import com.ws.hw1.controller.dto.post.PostDto;
import com.ws.hw1.model.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDto toDTO(Post post);
}
