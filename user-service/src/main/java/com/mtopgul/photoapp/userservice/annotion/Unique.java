package com.mtopgul.photoapp.userservice.annotion;

import com.mtopgul.photoapp.userservice.validator.UniqueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author muhammed-topgul
 * @since 21/09/2023 09:58
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UniqueValidator.class})
public @interface Unique {
    String message() default "{Field must be unique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> entity();

    String field() default "";
}
