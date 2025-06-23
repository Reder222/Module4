package com.application.util;

import com.application.dto.UserDTO;
import jakarta.validation.ConstraintViolation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class ErrorValidationUtil {

    private static Map<String, String> errors = new HashMap<>();


    public static void saveError(String name, Set<ConstraintViolation<Object>> constraintViolations) {

        if (constraintViolations != null && !constraintViolations.isEmpty()) {
            StringBuilder message = new StringBuilder();
            constraintViolations.forEach(constraintViolation -> {
                message.append(constraintViolation.getMessage()).append("\n");
            });
            errors.put(name, message.toString());
        }

    }

    public static String getError(String name) {
        String message = errors.get(name);
        if (message != null && !message.isEmpty()) {
            errors.remove(name);
            return message;
        }
        return null;
    }
}
