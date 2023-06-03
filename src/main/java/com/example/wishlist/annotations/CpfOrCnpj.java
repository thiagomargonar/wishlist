package com.example.wishlist.annotations;



import com.example.wishlist.validator.SuidValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {SuidValidator.class}
)
public @interface CpfOrCnpj {

    String message() default "Suid must be informed, have only numbers and be 11 or 14 digits long";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

