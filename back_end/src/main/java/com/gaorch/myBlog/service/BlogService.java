package com.gaorch.myBlog.service;

import com.gaorch.myBlog.entity.Blog;
import com.gaorch.myBlog.entity.User;
import com.gaorch.myBlog.mapper.*;
import com.gaorch.myBlog.utils.JwtUtil;
import com.gaorch.myBlog.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class BlogService
{
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BlogLikeService blogLikeService;
    @Autowired
    private BlogFavoriteService blogFavoriteService;
    @Autowired
    private BlogCommentService blogCommentService;
    @Autowired
    private BlogViewService blogViewService;

    @Autowired
    private HttpServletRequest request;


    public List<Blog> listAll()
    {
        return blogMapper.selectBasicBlogs();
    }

    public List<Blog> addOtherDataSize(List<Blog> blogs)
    {
        User user = userMapper.selectByAccount(JwtUtil.getAccountByToken(JwtUtil.getTokenByRequest(request)));
        Integer userId = user.getId();


        for(Blog curBlog: blogs)
        {
            Integer blogId = curBlog.getId();
            curBlog.setUsername(this.getUsernameByUserId(curBlog.getUserId()));
            curBlog.setLikeSize(blogLikeService.getLikeSizeByBlogId(blogId));
            curBlog.setFavoriteSize(blogFavoriteService.getFavoriteSizeByBlogId(blogId));
            curBlog.setCommentSize(blogCommentService.getCommentSizeByBlogId(blogId));
            curBlog.setViewSize(blogViewService.countViewsByBlogId(blogId));
            curBlog.setMyLike(blogLikeService.isMyLike(blogId, userId));
            curBlog.setMyFavorite(blogFavoriteService.isMyFavorite(blogId, userId));
            curBlog.setMyBlog(Objects.equals(userId, curBlog.getUserId()));
        }
        return blogs;
    }

    public List<Blog> filterDisplayable(List<Blog> blogs)
    {
        blogs.removeIf(curBlog -> !curBlog.getIsPublic() && !curBlog.getMyBlog());
        return blogs;
    }

    public List<Blog> getRecommendBlogs()
    {

        List<Blog> list = filterDisplayable(addOtherDataSize(listAll()));

        list.sort(new Comparator<Blog>() {
            @Override
            public int compare(Blog blog1, Blog blog2) {
                return Integer.compare(blog2.getRecommendIndex(), blog1.getRecommendIndex());
            }
        });
        return list;
    }

    public Result listRecommendAll()
    {
        System.out.println("按推荐排序");
        List<Blog> list = this.getRecommendBlogs();
        return Result.ok().data("items", list);
    }

    public Result listLatestAll()
    {
        System.out.println("按时间排序");
        List<Blog> list = filterDisplayable(addOtherDataSize(listAll()));
        list.sort(new Comparator<Blog>() {
            @Override
            public int compare(Blog blog1, Blog blog2) {
                LocalDateTime dateTime1 = LocalDateTime.parse(blog1.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                LocalDateTime dateTime2 = LocalDateTime.parse(blog2.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                return dateTime2.compareTo(dateTime1);
            }
        });
        return Result.ok().data("items", list);
    }


    public Result listMyBlogAll()
    {

        List<Blog> list = this.getRecommendBlogs();
        list.removeIf(curBlog -> !curBlog.getMyBlog());
        return Result.ok().data("items", list);
    }

    public Result listMyLikeAll()
    {
        List<Blog> list = this.getRecommendBlogs();
        list.removeIf(curBlog -> !curBlog.getMyLike());
        return Result.ok().data("items", list);
    }

    public Result listMyFavoriteAll()
    {
        List<Blog> list = this.getRecommendBlogs();
        list.removeIf(curBlog -> !curBlog.getMyFavorite());
        return Result.ok().data("items", list);
    }

    public Result getPostById(Integer blogId)
    {
        User user = userMapper.selectByAccount(JwtUtil.getAccountByToken(JwtUtil.getTokenByRequest(request)));
        Integer userId = user.getId();

        Blog blog = blogMapper.selectById(blogId);
        if (blog == null || (!blog.getIsPublic() && !blog.getUserId().equals(userId)))
            return Result.error();

        blog.setUsername(this.getUsernameByUserId(blog.getUserId()));
        blog.setLikeSize(blogLikeService.getLikeSizeByBlogId(blogId));
        blog.setFavoriteSize(blogFavoriteService.getFavoriteSizeByBlogId(blogId));
        blog.setViewSize(blogViewService.countViewsByBlogId(blogId));

        if(blogLikeService.isMyLike(blog.getId(), userId))
        {
            blog.setMyLike(true);
            blog.setMyLikeId(blogLikeService.getMyLikeId(blog.getId(), userId));
        }

        if(blogFavoriteService.isMyFavorite(blog.getId(), userId))
        {
            blog.setMyFavorite(true);
            blog.setMyFavoriteId(blogFavoriteService.getMyFavoriteId(blog.getId(), userId));
        }

        blogViewService.insert(userId, blogId);

        if(blog.getUserId().equals(userId))
            blog.setMyBlog(true);

        return Result.ok().data("items", blog);
    }


    public Result insert(Blog blog)
    {
        Integer userId = this.getUserIdByAccount(JwtUtil.getAccountByToken(JwtUtil.getTokenByRequest(request)));

        blog.setUserId(userId);
        blog.setId(0);
        int i = blogMapper.insert(blog);
        return i > 0 ? Result.ok() : Result.error();
    }

    public Result delete(Integer blogId)
    {
        blogLikeService.deleteLikesByBlogId(blogId);
        blogCommentService.deleteCommentsByBlogId(blogId);
        blogFavoriteService.deleteFavoritesByBlogId(blogId);
        blogViewService.deleteByBlogId(blogId);
        return blogMapper.deleteById(blogId) > 0 ? Result.ok() : Result.error();
    }

    public Result updateBlogStatusByBlogId(Integer blogId)
    {
        Blog blog = blogMapper.selectById(blogId);
        blog.setIsPublic(!blog.getIsPublic());
        int i = blogMapper.updateById(blog);
        return i > 0 ? Result.ok() : Result.error();
    }

    public Result updateBlogByBlogId(Integer blogId, Blog blog)
    {
        Blog originalBlog = blogMapper.selectById(blogId);
        originalBlog.setContent(blog.getContent());
        originalBlog.setTitle(blog.getTitle());
        originalBlog.setIsPublic(blog.getIsPublic());
        int i = blogMapper.updateById(originalBlog);
        return i > 0 ? Result.ok() : Result.error();
    }


    public Boolean deleteAllByUserId(Integer userId)
    {
        List<Blog> blogs = blogMapper.selectByUserId(userId);
        for (Blog curBlog : blogs) {
            this.delete(curBlog.getId());
        }

        blogLikeService.deleteAllByUserId(userId);
        blogFavoriteService.deleteAllByUserId(userId);
        blogCommentService.deleteAllByUserId(userId);
        blogViewService.deleteByUserId(userId);

        return true;
    }

    public Integer getUserIdByAccount(String account)
    {
        return userMapper.getIdByAccount(account);
    }

    public String getUsernameByUserId(Integer userId)
    {
        return userMapper.selectById(userId).getUsername();
    }

}
