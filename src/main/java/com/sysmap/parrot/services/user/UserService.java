package com.sysmap.parrot.services.user;

import com.sysmap.parrot.services.enumeration.RoleEnum;
import com.sysmap.parrot.services.security.IJwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sysmap.parrot.data.IUserRepository;
import com.sysmap.parrot.entities.User;

@Service
public class UserService implements IUserService{
    @Autowired
    private IUserRepository _userRepository;
    @Autowired
	private IJwtService _jwtService;
	//@Autowired
	//private PasswordEncoder _passwordEncoder;

    public void createUser(CreateUserRequest request) {
		//var passwordEncrypted = _passwordEncoder.encode(request.getPassword());
        User user = new User(request.getName(), request.getEmail(), request.getPassword());
		user.setRole(RoleEnum.USER);

        if(_userRepository.findFirstUserByEmail(user.getEmail()).isPresent()) {
        	throw new RuntimeException("User already exists!");
    	}

		_userRepository.save(user);
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
	/*
	public List<FindUserResponse> findAll() {
		List<User> users = _userRepository.findAll();
		List<FindUserResponse> response = new ArrayList<FindUserResponse>(); 
		System.out.println();
		for(User user : users) {
			response.add(new FindUserResponse(user.getId(), user.getName(), user.getEmail()));
		}
				
		return response;
	}*/
}
