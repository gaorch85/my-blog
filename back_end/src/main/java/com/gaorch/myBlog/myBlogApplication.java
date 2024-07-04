package com.gaorch.myBlog;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.gaorch.myBlog.mapper")
@SpringBootApplication
public class myBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(myBlogApplication.class, args);
    }

}
