package com.sysmap.parrot.services.user;

import com.sysmap.parrot.entities.User;
import org.springframework.web.multipart.MultipartFile;

public interface IUserService {
    void createUser(CreateUserRequest request);
    FindUserResponse findUserByEmail(String email);
    User getUser(String email);
    void uploadPhotoProfile(MultipartFile photo) throws Exception;
}
