package com.blog.app.apis.payloads;

import lombok.*;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Data
public class PostResponse {


    private List<PostDto> content;
    private int pageNumber;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private boolean lastPage;
}
