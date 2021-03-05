package pl.umk.mat.booking.validation.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ANNOTATION_TYPE, CONSTRUCTOR, FIELD, METHOD, PARAMETER})
@Retention(RUNTIME)
public @interface Password {
    String message() default "Password should contain at least one digit, one capital letter, one small letter and at least 8 characters";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
