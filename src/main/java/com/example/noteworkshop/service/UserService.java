package com.example.noteworkshop.service;

import com.example.noteworkshop.dto.LoginDTO;
import com.example.noteworkshop.dto.RegisterDTO;
import com.example.noteworkshop.entity.User;

public interface UserService {
    User getUserById(Long id);

    // 新增：注册
    void register(RegisterDTO registerDTO);

    // 新增：登录，返回 Token
    String login(LoginDTO loginDTO);
}