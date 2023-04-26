package com.licenta2023.backend.service;

import com.licenta2023.backend.domain.User;
import com.licenta2023.backend.repository.RepositoryInterfaces.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static com.licenta2023.backend.utils.Constants.EMAIL_PATTERN;
import static com.licenta2023.backend.utils.Constants.USERNAME_PATTERN;


@Service
public class UserService implements IUserService{

    @Autowired
    public IUserRepo userRepo;

    public UserService(IUserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User findOne(Long id) {
        return userRepo.findOne(id);
    }

    @Override
    public Iterable<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public void save(User user) {
        userRepo.save(user);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(User entity) {

    }

    @Override
    public User findUserByEmail(String email) {
        return userRepo.findOneByEmail(email);
    }


    @Override
    public boolean validateUser(User user) {
        if (user.getName() == null || user.getEmail() == null) {
            return false;
        }

        Matcher mUsername = USERNAME_PATTERN.matcher(user.getName());
        Matcher mEmail = EMAIL_PATTERN.matcher(user.getEmail());

        return mUsername.matches() && mEmail.matches();
    }
}
