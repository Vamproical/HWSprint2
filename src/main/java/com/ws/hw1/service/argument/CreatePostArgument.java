package com.ws.hw1.service.argument;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreatePostArgument {
    String name;
}
