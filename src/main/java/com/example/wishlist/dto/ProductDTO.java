package com.example.wishlist.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class ProductDTO {

    private String id;
    @NotNull
    private String productName;
    @NotNull
    @PositiveOrZero
    private BigDecimal value;
    @NotNull
    private String urlImage;

    private ProductDTO() {
    }

    private ProductDTO(Builder builder) {
        id = builder.id;
        productName = builder.productName;
        value = builder.value;
        urlImage = builder.urlImage;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getUrlImage() {
        return urlImage;
    }


    public static final class Builder {
        private String id;
        private String productName;
        private BigDecimal value;
        private String urlImage;

        private Builder() {
        }

        public Builder withId(String val) {
            id = val;
            return this;
        }

        public Builder withProductName(String val) {
            productName = val;
            return this;
        }

        public Builder withValue(BigDecimal val) {
            value = val;
            return this;
        }

        public Builder withUrlImage(String val) {
            urlImage = val;
            return this;
        }

        public ProductDTO build() {
            return new ProductDTO(this);
        }
    }
}
