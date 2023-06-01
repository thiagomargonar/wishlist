package com.example.wishlist.dto;

import java.math.BigDecimal;

public class WishlistDTO {

    private String productName;

    private BigDecimal value;

    private String urlImage;

    public WishlistDTO() {
    }

    private WishlistDTO(Builder builder) {
        productName = builder.productName;
        value = builder.value;
        urlImage = builder.urlImage;
    }

    public static Builder builder() {
        return new Builder();
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
        private String productName;
        private BigDecimal value;
        private String urlImage;

        private Builder() {
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

        public WishlistDTO build() {
            return new WishlistDTO(this);
        }
    }
}
