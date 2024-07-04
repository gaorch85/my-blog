package com.gaorch.myBlog.entity;

import lombok.Data;

@Data
public class BlogView {
    private Integer id;
    private Integer userId;
    private Integer blogId;
    private String createdAt;
}
