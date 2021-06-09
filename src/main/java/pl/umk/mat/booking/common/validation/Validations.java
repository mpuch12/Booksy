package pl.umk.mat.booking.common.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Validations {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static Optional<String> validate(Object object) {
        var constraintViolations = validator.validate(object);
        if (constraintViolations.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(createMessage(constraintViolations));
        }
    }

    private static String createMessage(Set<ConstraintViolation<Object>> constraintViolations) {
        var collect = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        return String.join(", ", collect);
    }
}
