package com.gaorch.myBlog.service;

import com.gaorch.myBlog.entity.User;
import com.gaorch.myBlog.mapper.UserMapper;
import com.gaorch.myBlog.utils.JwtUtil;
import com.gaorch.myBlog.utils.PasswordUtil;
import com.gaorch.myBlog.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BlogService blogService;

    @Autowired
    private HttpServletRequest request;     //用于解析请求头中的token

    public Result login(User user)
    {
        User selectUser = userMapper.selectByAccount(user.getAccount());
        if(selectUser != null)
        {
            String salt = selectUser.getSalt();
            String curHashPassword = PasswordUtil.hashPassword(user.getPassword(), salt);
            if(curHashPassword.equals(selectUser.getPassword()))
            {
                String token = JwtUtil.generateToken(user.getAccount());
                System.out.println(token);
                return Result.ok().data("token", token);
            }
        }
        return Result.error();
    }

    public Result signup(User user)
    {
        user.setSalt(PasswordUtil.generateSalt());
        user.setPassword(PasswordUtil.hashPassword(user.getPassword(), user.getSalt()));
        User selectUser = userMapper.selectByAccount(user.getAccount());
        if(selectUser == null)
        {
            int i = userMapper.insert(user);
            if(i > 0)
                return Result.ok();
        }
        return Result.error();
    }

    public Result getInfo()
    {
        User user = userMapper.selectByAccount(JwtUtil.getAccountByToken(JwtUtil.getTokenByRequest(request)));
        user.setUsername(user.getUsername());
        if (user != null)
            return Result.ok().data("items", user);
        else
            return Result.error();
    }

    public Result changePassword(User user)
    {
        String account = JwtUtil.getAccountByToken(JwtUtil.getTokenByRequest(request));
        String password = user.getPassword();
        User selectUser = userMapper.selectByAccount(account);
        if (selectUser != null)
        {
            selectUser.setSalt(PasswordUtil.generateSalt());
            selectUser.setPassword(PasswordUtil.hashPassword(password, selectUser.getSalt()));
            int i = userMapper.updateById(selectUser);
            if(i>0)
                return Result.ok();
        }
        return Result.error();
    }

    public Result update(User user)
    {
        int i = userMapper.updateById(user);
        return i > 0 ? Result.ok() : Result.error();
    }

    public Result delete()
    {
        User user = userMapper.selectByAccount(JwtUtil.getAccountByToken(JwtUtil.getTokenByRequest(request)));
        Integer userId = user.getId();

        blogService.deleteAllByUserId(userId);


        return userMapper.deleteById(userId) > 0 ?
                Result.ok() : Result.error();
    }


}
