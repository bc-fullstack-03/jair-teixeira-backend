package com.sysmap.parrot.services.user;

import com.sysmap.parrot.services.enumeration.RoleEnum;
import com.sysmap.parrot.services.security.IJwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	@Autowired
	private UserDetailsService _userDetailsService;
	@Autowired
	private PasswordEncoder _passwordEncoder;

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
}
