package com.gaorch.myBlog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gaorch.myBlog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM user WHERE account = #{account}")
    public User selectByAccount(String account);

    @Select("SELECT id FROM user WHERE account = #{account}")
    public Integer getIdByAccount(String account);

}