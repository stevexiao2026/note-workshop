package com.example.noteworkshop.controller;

import com.example.noteworkshop.common.result;
import com.example.noteworkshop.dto.LoginDTO;
import com.example.noteworkshop.dto.RegisterDTO;
import com.example.noteworkshop.entity.User;
import com.example.noteworkshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public result<User> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return result.success(user);
    }

    // 注册接口
    @PostMapping("/register")
    public result<String> register(@RequestBody RegisterDTO registerDTO) {
        userService.register(registerDTO);
        return result.success("注册成功");
    }

    // 登录接口
    @PostMapping("/login")
    public result<String> login(@RequestBody LoginDTO loginDTO) {
        String token = userService.login(loginDTO);
        return result.success(token);
    }
}