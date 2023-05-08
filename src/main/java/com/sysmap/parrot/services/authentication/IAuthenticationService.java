package com.sysmap.parrot.services.authentication;

public interface IAuthenticationService {
	AuthenticateResponse authenticate(AuthenticateRequest request);
}
