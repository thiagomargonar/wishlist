package com.example.wishlist.validator;


import com.example.wishlist.annotations.MaxWishlist;
import com.example.wishlist.dto.WishlistDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class MaxWishListValidator implements ConstraintValidator<MaxWishlist, List<WishlistDTO>> {

    private int value;

    @Override
    public void initialize(MaxWishlist constraintAnnotation) {
        this.value = constraintAnnotation.value();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<WishlistDTO> list, ConstraintValidatorContext context) {
        return list != null && list.size() < value;
    }
}
