package com.gaorch.myBlog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


@Data
public class Blog {
    private Integer id;
    private Integer userId;
    private String time;
    private String title;
    private String content;
    private Boolean isPublic;

    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private Integer likeSize = 0;
    @TableField(exist = false)
    private Integer favoriteSize = 0;
    @TableField(exist = false)
    private Integer commentSize = 0;
    @TableField(exist = false)
    private Integer viewSize = 0;

    @TableField(exist = false)
    private Boolean myLike = false;
    @TableField(exist = false)
    private Integer myLikeId;
    @TableField(exist = false)
    private Boolean myFavorite = false;
    @TableField(exist = false)
    private Integer myFavoriteId;
    @TableField(exist = false)
    private Boolean myBlog = false;

    @JsonIgnore
    public int getRecommendIndex()
    {
        return this.getLikeSize() + this.getFavoriteSize() + this.getCommentSize() + this.getViewSize();
    }

}
