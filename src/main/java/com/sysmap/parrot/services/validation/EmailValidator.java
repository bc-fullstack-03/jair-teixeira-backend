package com.sysmap.parrot.services.validation;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailValidator {
    public boolean isValid(String email) {
        if(emailFormatAccepted(email)) {
            return true;
        } else {
            throw new RuntimeException("Invalid email format.");
        }
    }
    private boolean emailFormatAccepted(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern emailPattern = Pattern.compile(emailRegex);

        return emailPattern.matcher(email).matches();
    }


}
