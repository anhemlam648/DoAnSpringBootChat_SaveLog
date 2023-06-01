package com.example._VuTrungNghia_SQL.validator;

import com.example._VuTrungNghia_SQL.entity.User;
import com.example._VuTrungNghia_SQL.validator.annotiton.ValidUserId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidUserIdValidator implements ConstraintValidator<ValidUserId, User> {

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context){
        if(user == null)
            return true;
        return user.getId() != null;
    }
}
