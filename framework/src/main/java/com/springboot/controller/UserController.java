package com.springboot.controller;

import com.springboot.domain.vo.UserVo;
import com.springboot.domain.myMethord.Result;
import com.springboot.domain.entity.User;
import com.springboot.service.UserService;
import com.springboot.utils.JwtUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;



    @PostMapping("/login")//@RequestParam String uname, @RequestParam String password
    public Result loginController(@RequestBody UserVo user){
        User user2 = userService.loginService(user.getAccount(), user.getPassword());
        if(user2!=null){

            String token;
            token = JwtUtil.createJWT(UUID.randomUUID().toString(), String.valueOf(user2.getUid()), null);
            return Result.success(token,user2.getUid());
        }else{
            //return new Result<>(300,"用户名或密码错误，请重新登录");
            return Result.error("300","用户名或密码错误，请重新登录");
        }
    }

    @PostMapping("/register")
    public Result<User> registController(@RequestBody UserVo newUser){
        return userService.registService(newUser.getAccount(),newUser.getPassword());

    }
}