package com.sysmap.parrot.services.user;

import com.sysmap.parrot.services.enumeration.RoleEnum;
import com.sysmap.parrot.services.fileUpload.IFileUploadService;
import com.sysmap.parrot.services.security.IJwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sysmap.parrot.data.IUserRepository;
import com.sysmap.parrot.entities.User;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService implements IUserService{
    @Autowired
    private IUserRepository _userRepository;
    @Autowired
	private IJwtService _jwtService;
	@Autowired
	private PasswordEncoder _passwordEncoder;
	@Autowired
	private IFileUploadService _fileUploadService;

	public void createUser(CreateUserRequest request) {
		var user = new User(request.getName(),
							request.getEmail(),
							_passwordEncoder.encode(request.getPassword()),
							RoleEnum.USER);

		if(_userRepository.findFirstUserByEmail(user.getEmail()).isPresent()) {
        	throw new RuntimeException("User already exists!");
    	}

		_userRepository.save(user).getEmail();
    }
	public FindUserResponse findUserByEmail(String email) {
		User user = getUser(email);
		FindUserResponse response = new FindUserResponse(user.getId(),
															user.getName(),
															user.getEmail());
		return response;
	}
	public User getUser(String email) {
		if(_userRepository.findFirstUserByEmail(email).isEmpty()) {
        	throw new RuntimeException("User doesn't exist!");
    	}

		return _userRepository.findFirstUserByEmail(email).get();
	}

	public void uploadPhotoProfile(MultipartFile photo) throws Exception {

		var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

		var photoUri = "";

		try {
			var fileName = user.getId() + "." + photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1);
			photoUri = _fileUploadService.upload(photo, fileName);
		} catch (Exception e) {
			throw new Exception(e.getMessage());

		}
		user.setPhotoUri(photoUri);
		_userRepository.save(user);
	}
}
