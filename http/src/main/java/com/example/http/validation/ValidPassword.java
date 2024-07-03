package com.example.http.validation;

import com.example.http.validation.Impl.PasswordValidationImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidationImpl.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Password must contain at least three of the following: uppercase letters, lowercase letters, digits, and special characters";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
