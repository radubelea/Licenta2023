package com.licenta2023.backend.domain.validator;

import com.licenta2023.backend.domain.User;

public class UserValidator implements Validator<User> {
    @Override
    public void validate(User entity) throws ValidationException {
        String errors="";
        if("".equals(entity.getName().trim()))
            errors+="Nume de utilizator invalid!\n";
        if("".equals(entity.getPassword().trim()))
            errors+="Parola invalida!\n";
        if(errors.length() > 0){
            throw new ValidationException(errors);
        }
    }
}
