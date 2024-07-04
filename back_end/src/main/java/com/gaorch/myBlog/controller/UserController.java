package com.gaorch.myBlog.controller;

import com.gaorch.myBlog.entity.User;
import com.gaorch.myBlog.service.UserService;
import com.gaorch.myBlog.utils.Response;
import com.gaorch.myBlog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public Response signup(@RequestBody User user)
    {
        Result result = userService.signup(user);
        return result.isSuccess() ? Response.ok() : Response.error();
    }

    @PostMapping("/login")
    public Response login(@RequestBody User user)
    {
        Result result = userService.login(user);
        return result.isSuccess() ?
                Response.ok().setData(result.getData()) : Response.error();
    }

    @GetMapping("/getInfo")
    public Response getInfo() {
        Result result = userService.getInfo();
        return result.isSuccess() ?
                Response.ok().setData(result.getData()) : Response.error();
    }

    @PutMapping("/changePassword")
    public Response changePassword(@RequestBody User user) {
        Result result = userService.changePassword(user);
        return result.isSuccess() ? Response.ok() : Response.error();
    }

    @PutMapping("/user/update")
    public Response update(@RequestBody User user) {
        Result result = userService.update(user);
        return result.isSuccess() ? Response.ok() : Response.error();
    }

    @DeleteMapping("/user")
    public Response deactivateAccount() {
        Result result = userService.delete();
        return result.isSuccess() ? Response.ok() : Response.error();
    }

}
