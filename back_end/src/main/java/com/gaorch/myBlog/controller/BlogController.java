package com.gaorch.myBlog.controller;

import com.gaorch.myBlog.entity.Blog;
import com.gaorch.myBlog.entity.BlogComment;
import com.gaorch.myBlog.service.BlogCommentService;
import com.gaorch.myBlog.service.BlogFavoriteService;
import com.gaorch.myBlog.service.BlogLikeService;
import com.gaorch.myBlog.service.BlogService;
import com.gaorch.myBlog.utils.Response;
import com.gaorch.myBlog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private BlogLikeService blogLikeService;
    @Autowired
    private BlogFavoriteService blogFavoriteService;
    @Autowired
    private BlogCommentService blogCommentService;


    @GetMapping("/blog/list/{sortIndex}")
    public Response getAll(@PathVariable String sortIndex)
    {
        System.out.println(sortIndex);
        Result result = Result.error();
        if("latest".equals(sortIndex))
            result = blogService.listLatestAll();
        if("myBlog".equals(sortIndex))
            result = blogService.listMyBlogAll();
        if("myLike".equals(sortIndex))
            result = blogService.listMyLikeAll();
        if("myFavorite".equals(sortIndex))
            result = blogService.listMyFavoriteAll();
        if("recommend".equals(sortIndex))
            result = blogService.listRecommendAll();
        return result.isSuccess() ?
                Response.ok().setData(result.getData()) : Response.error();
    }

    @GetMapping("/blog/{postId}")
    public Response getBlogById(@PathVariable Integer postId)
    {
        //System.out.println(postId);
        Result result = blogService.getPostById(postId);
        return result.isSuccess() ?
                Response.ok().setData(result.getData()) : Response.error();
    }

    @DeleteMapping("/blog/{postId}")
    public Response deleteBlogById(@PathVariable Integer postId)
    {
        Result result = blogService.delete(postId);
        return result.isSuccess() ? Response.ok() : Response.error();
    }

    @PutMapping("/blog/update_status/{postId}")
    public Response updateBlogStatusByBlogId(@PathVariable Integer postId)
    {
        Result result = blogService.updateBlogStatusByBlogId(postId);
        return result.isSuccess() ? Response.ok() : Response.error();
    }

    @PutMapping("/blog/{postId}")
    public Response updateBlogById(@PathVariable Integer postId, @RequestBody Blog blog)
    {
        Result result = blogService.updateBlogByBlogId(postId, blog);
        return result.isSuccess() ? Response.ok() : Response.error();
    }

    @PostMapping("/blog")
    public Response insertBlog(@RequestBody Blog blog)
    {
        Result result = blogService.insert(blog);
        return result.isSuccess() ? Response.ok() : Response.error();
    }
    
    @PostMapping("/blog/like/{postId}")
    public Response insertLike(@PathVariable Integer postId)
    {
        Result result = blogLikeService.insert(postId);
        return result.isSuccess() ? Response.ok() : Response.error();
    }

    @DeleteMapping("/blog/like/{likeId}")
    public Response deleteLike(@PathVariable Integer likeId)
    {
        Result result = blogLikeService.delete(likeId);
        return result.isSuccess() ? Response.ok() : Response.error();
    }

    @PostMapping("/blog/favorite/{postId}")
    public Response insertFavorite(@PathVariable Integer postId)
    {
        Result result = blogFavoriteService.insert(postId);
        return result.isSuccess() ? Response.ok() : Response.error();
    }

    @DeleteMapping("/blog/favorite/{favoriteId}")
    public Response deleteFavorite(@PathVariable Integer favoriteId)
    {
        Result result = blogFavoriteService.delete(favoriteId);
        return result.isSuccess() ? Response.ok() : Response.error();
    }

    @GetMapping("/blog/comment/{postId}")
    public Response getCommentsByBlogId(@PathVariable Integer postId)
    {
        System.out.println(postId);
        Result result = blogCommentService.getCommentsByBlogId(postId);
        return result.isSuccess() ?
                Response.ok().setData(result.getData()) : Response.error();
    }

    @PostMapping("/blog/comment/{postId}")
    public Response addComment(@PathVariable Integer postId,@RequestBody BlogComment blogComment)
    {
        System.out.println(blogComment);
        Result result = blogCommentService.insert(postId, blogComment.getContent());
        return result.isSuccess() ? Response.ok() : Response.error();
    }

    @DeleteMapping("/blog/comment/{id}")
    public Response deleteComment(@PathVariable Integer id)
    {
        Result result = blogCommentService.delete(id);
        return result.isSuccess() ? Response.ok() : Response.error();
    }

}
