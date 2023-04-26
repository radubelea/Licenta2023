package com.licenta2023.backend.utils;

import org.springframework.stereotype.Component;

import static com.licenta2023.backend.utils.Constants.*;


@Component
public class Validator {

    /**
     * Validate a password
     *
     * @return true if password is valid
     * false , otherwise
     */
    public boolean validatePassword(String password) {
        return password.matches(PASSWORD_PATTERN.toString());
    }


    /**
     * Validate an username
     *
     * @return true if user is valid
     * false , otherwise
     */
    public boolean validateUsername(String username) {
        return username.matches(USERNAME_PATTERN.toString());
    }

    /**
     * Validate an email
     *
     * @return true if user is valid
     * false , otherwise
     */
    public boolean validateEmail(String email) {
        return email.matches(EMAIL_PATTERN.toString());
    }

}
