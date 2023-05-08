package com.sysmap.parrot.services.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
	private AuthenticationManager _authenticationManager;
	public AuthenticateResponse authenticate(AuthenticateRequest request) {
		/*_authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()
				)
		);*/
		User user = _userService.getUser(request.getEmail());
		
		if(!user.getPassword().equals(request.getPassword())) {
			throw new RuntimeException("Incorrect password!");
		}
		
		AuthenticateResponse response = new AuthenticateResponse();
		response.setToken(_jwtService.generateToken(user));
		return response;
	}
	
}
