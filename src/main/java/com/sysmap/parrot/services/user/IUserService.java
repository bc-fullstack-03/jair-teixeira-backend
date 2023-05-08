package com.sysmap.parrot.services.user;

import com.sysmap.parrot.entities.User;

public interface IUserService {
    void createUser(CreateUserRequest request);
    FindUserResponse findUserByEmail(String email);
    User getUser(String email);
}
