package com.licenta2023.backend.repository.RepositoryInterfaces;

import com.licenta2023.backend.domain.User;

public interface IUserRepo extends IRepo<User> {
    User findOneByEmail(String email);
}
