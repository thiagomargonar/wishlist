package com.example.wishlist.validator;


import com.example.wishlist.annotations.CpfOrCnpj;
import org.springframework.util.StringUtils;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class SuidValidator implements ConstraintValidator<CpfOrCnpj, CharSequence> {

    private final Pattern pattern = Pattern.compile("^\\d{11}$|^\\d{14}$");

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
        if (!StringUtils.hasLength(value)) {
            return false;
        }

        return pattern.matcher(value).matches();
    }
}
