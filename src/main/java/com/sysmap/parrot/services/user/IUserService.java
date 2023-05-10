package com.sysmap.parrot.services.user;

import com.sysmap.parrot.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface IUserService {
    void createUser(CreateUserRequest request);
    FindUserResponse findUserByEmail(String email);
    User getUser(String email);
    void uploadPhotoProfile(MultipartFile photo);
    User getUserById(UUID id);
}
