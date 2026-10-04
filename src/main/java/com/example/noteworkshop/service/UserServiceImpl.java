package com.example.noteworkshop.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.noteworkshop.exception.BusinessException;
import com.example.noteworkshop.dto.LoginDTO;
import com.example.noteworkshop.dto.RegisterDTO;
import com.example.noteworkshop.entity.User;
import com.example.noteworkshop.mapper.UserMapper;
import com.example.noteworkshop.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public void register(RegisterDTO registerDTO) {
        // 1. 检查用户名是否已存在
        User existUser = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, registerDTO.getUsername()));
        if (existUser != null) {
            throw new BusinessException("用户名已存在，请更换");
        }

        // 2. 构建新用户，密码加密
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword())); // 密码加密
        user.setNickname(registerDTO.getNickname());
        user.setRole("user");
        userMapper.insert(user);
    }

    @Override
    public String login(LoginDTO loginDTO) {
        // 1. 根据用户名查用户
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, loginDTO.getUsername()));
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }

        // 2. 比对密码（加密后的比对）
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        // 3. 生成 JWT Token 并返回
        return jwtUtil.generateToken(user.getId(), user.getUsername());
    }
}