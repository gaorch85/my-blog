package com.gaorch.myBlog.entity;

import lombok.Data;

@Data
public class BlogLike {
    private Integer id;
    private Integer userId;
    private Integer blogId;
    private String createdAt;
}
