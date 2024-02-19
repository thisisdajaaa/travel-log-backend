package com.travellog.travellog.helpers;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Arrays;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class DTOHelper {
    @Target(TYPE)
    @Retention(RUNTIME)
    @Constraint(validatedBy = AtLeastOneFieldValidator.class)
    public @interface AtLeastOneFieldNotEmpty {
        String message() default "At least one field must be provided!";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

    public static class AtLeastOneFieldValidator implements ConstraintValidator<AtLeastOneFieldNotEmpty, Object> {
        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            return Arrays.stream(value.getClass().getDeclaredFields())
                    .anyMatch(field -> {
                        try {
                            field.setAccessible(true);
                            Object fieldValue = field.get(value);

                            if (fieldValue instanceof String)
                                return !((String) fieldValue).trim().isEmpty();

                            return fieldValue != null;
                        } catch (IllegalAccessException e) {
                            System.out.println(e.getMessage());
                            return false;
                        }
                    });
        }
    }
}
