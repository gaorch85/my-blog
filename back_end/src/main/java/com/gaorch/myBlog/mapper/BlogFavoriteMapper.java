package com.gaorch.myBlog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gaorch.myBlog.entity.BlogFavorite;
import com.gaorch.myBlog.entity.BlogLike;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BlogFavoriteMapper extends BaseMapper<BlogFavorite> {
    @Select("SELECT * FROM blog_favorite WHERE blog_id = #{blogId}")
    public List<BlogFavorite> selectByBlogId(Integer blogId);

    @Select("SELECT COUNT(*) FROM blog_favorite WHERE blog_id = #{blogId}")
    public Integer getFavoriteSizeByBlogId(Integer blogId);

    @Select("SELECT id FROM blog_favorite WHERE blog_id = #{blogId} AND user_id = #{userId}")
    public List<Integer> getMyFavoriteId(Integer blogId, Integer userId);

    @Delete("DELETE FROM blog_favorite WHERE blog_id = #{blogId}")
    public Boolean deleteFavoritesByBlogId(Integer blogId);

    @Delete("DELETE FROM blog_favorite WHERE user_id = #{userId}")
    public Boolean deleteAllByUserId(Integer userId);

}
