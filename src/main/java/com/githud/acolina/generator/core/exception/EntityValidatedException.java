package com.githud.acolina.generator.core.exception;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Angel Colina
 * @version 1.0
 */
public class EntityValidatedException extends Exception {

    private Set<ConstraintViolation<Object>> violations;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     *
     * @param violations
     */
    public EntityValidatedException(Set<ConstraintViolation<Object>> violations) {
        super("Error validating Entity");
        this.violations = violations;
    }

    public Set<ConstraintViolation<Object>> getViolations() {
        return violations;
    }

    public Map<String, List<String>> getErrors() {
        return violations.stream()
                .collect(Collectors.groupingBy(e -> e.getPropertyPath().toString(),
                        Collectors.collectingAndThen(Collectors.toList(),
                                e -> e.stream()
                                        .map(ConstraintViolation::getMessage)
                                        .collect(Collectors.toList()))));
    }

}
