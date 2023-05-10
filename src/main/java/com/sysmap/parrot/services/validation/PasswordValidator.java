package com.sysmap.parrot.services.validation;

import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {
	public boolean isValid(String password){
        if(password.length() > 5) {
            return true;
        } else {
            throw new RuntimeException("Invalid password length.");
        }
    }

}
