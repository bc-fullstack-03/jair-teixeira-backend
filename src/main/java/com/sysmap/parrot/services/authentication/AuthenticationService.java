package com.sysmap.parrot.services.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sysmap.parrot.entities.User;
import com.sysmap.parrot.services.security.IJwtService;
import com.sysmap.parrot.services.user.IUserService;

@Service
public class AuthenticationService implements IAuthenticationService {
	@Autowired
	private IUserService _userService;
	@Autowired
	private IJwtService _jwtService;
	@Autowired
	private PasswordEncoder _passwordEncoder;

	public AuthenticateResponse authenticate(AuthenticateRequest request) {
		User user = _userService.getUser(request.getEmail());

		if (!_passwordEncoder.matches(request.password, user.getPassword())) {
			throw new RuntimeException("Invalid credentials!");
		}
		
		AuthenticateResponse response = new AuthenticateResponse();
		response.setToken(_jwtService.generateToken(user));
		return response;
	}
	
}
