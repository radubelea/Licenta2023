package com.licenta2023.backend.service;

import com.licenta2023.backend.domain.User;

public interface IUserService extends IService<User>{
    public User findUserByEmail(String email);
    public boolean validateUser(User user);
}
