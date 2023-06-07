package com.example.wishlist.annotations;


import com.example.wishlist.validator.MaxWishListValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {MaxWishListValidator.class}
)
public @interface MaxWishlist {

    String message() default "Number max in wish list.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int value() default 20;
}

