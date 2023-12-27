package com.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostResponse {
    private List<PostDto> postDtoList;
    private int pageNum;
    private int pageSize;
    private long totalElement;
    private boolean lastPage;
}
