package com.gaorch.myBlog.service;

import com.gaorch.myBlog.entity.User;
import com.gaorch.myBlog.mapper.BlogCommentMapper;
import com.gaorch.myBlog.entity.BlogComment;
import com.gaorch.myBlog.mapper.UserMapper;
import com.gaorch.myBlog.utils.JwtUtil;
import com.gaorch.myBlog.utils.IpParseUtil;
import com.gaorch.myBlog.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogCommentService {

    @Autowired
    private BlogCommentMapper blogCommentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HttpServletRequest request;

    public Result getCommentsByBlogId(Integer blogId)
    {
        List<BlogComment> comments = blogCommentMapper.selectByBlogId(blogId);

        User user = userMapper.selectByAccount(JwtUtil.getAccountByToken(JwtUtil.getTokenByRequest(request)));
        Integer userId = user.getId();

        for(BlogComment curComment: comments)
        {
            User curUser = userMapper.selectById(curComment.getUserId());
            curComment.setUsername(curUser.getUsername());
            curComment.setMyComment(userId);
        }
        return Result.ok().data("items",comments);
    }

    public Boolean deleteCommentsByBlogId(Integer blogId)
    {
        return blogCommentMapper.deleteCommentsByBlogId(blogId);
    }

    public Boolean deleteAllByUserId(Integer userId)
    {
        return blogCommentMapper.deleteAllByUserId(userId);
    }

    public Integer getCommentSizeByBlogId(Integer blogId)
    {
        return blogCommentMapper.getCommentSizeByBlogId(blogId);
    }

    public Result insert(Integer postId, String comment)
    {
        String ipAddress = request.getRemoteAddr();


        User user = userMapper.selectByAccount(JwtUtil.getAccountByToken(JwtUtil.getTokenByRequest(request)));
        Integer userId = user.getId();

        BlogComment blogComment = new BlogComment();
        blogComment.setUserId(userId);
        blogComment.setBlogId(postId);
        blogComment.setId(0);
        blogComment.setContent(comment);

        String r = "";
        int[] index = {0, 3};
        List<String> parse = IpParseUtil.parse(ipAddress, index);
        for (String element : parse) {
            r = r.concat(element).concat(" ");
        }
        blogComment.setRegion(r);


        int i = blogCommentMapper.insert(blogComment);
        return i > 0 ? Result.ok() : Result.error();
    }

    public Result delete(Integer id)
    {
        int i = blogCommentMapper.deleteById(id);
        return i > 0 ? Result.ok() : Result.error();
    }
}
