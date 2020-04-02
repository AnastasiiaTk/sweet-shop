package com.anastasiiat.sweetshop.validator;

import com.anastasiiat.sweetshop.service.UserService;
import com.anastasiiat.sweetshop.persistence.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        User user = (User) object;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() < 5 || user.getUsername().length() > 256) {
            errors.rejectValue("username", "size.userForm.username");
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "duplicate.userForm.username");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "notEmpty");
        if (user.getPassword().length() < 5) {
            errors.rejectValue("password", "size.userForm.password");
        }
        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "diff.userForm.passwordConfirm");
        }
    }
}
