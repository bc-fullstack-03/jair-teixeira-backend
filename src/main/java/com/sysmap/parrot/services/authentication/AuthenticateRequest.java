package com.sysmap.parrot.services.authentication;

import lombok.Data;

@Data
public class AuthenticateRequest {
	public String email;
	public String password;
}
