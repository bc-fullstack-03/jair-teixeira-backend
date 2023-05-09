package com.sysmap.parrot.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sysmap.parrot.services.security.IJwtService;
import com.sysmap.parrot.services.user.CreateUserRequest;
import com.sysmap.parrot.services.user.IUserService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private IUserService _userService;
    @Autowired
    private IJwtService _jwtService;

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request){
        _userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping()
    public ResponseEntity<?> getUser(String email){
    	return ResponseEntity.ok().body(_userService.findUserByEmail(email));
    }
    @PostMapping("/photo/upload")
    public ResponseEntity uploadPhotoProfile(@RequestParam("photo") MultipartFile photo) {
        try {
            _userService.uploadPhotoProfile(photo);

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}