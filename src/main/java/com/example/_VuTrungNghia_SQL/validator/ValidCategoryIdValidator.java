package com.example._VuTrungNghia_SQL.validator;


import com.example._VuTrungNghia_SQL.entity.Category;
import com.example._VuTrungNghia_SQL.validator.annotiton.ValidCategoryId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidCategoryIdValidator implements ConstraintValidator<ValidCategoryId, Category> {
    @Override
    public boolean isValid(Category category, ConstraintValidatorContext context){
        return category != null && category.getId() != null;
    }
}
