package com.example.wishlist.dto;

import java.util.List;

public class WishlistDTO {
    private Boolean finished;

//    @Size(max = 20, message = "The limit of 20 products has been exceeded, surpassing the maximum size.")
//    @NotEmpty
//    @Valid
    private List<ProductDTO> products;

    public WishlistDTO() {
    }

    private WishlistDTO(Builder builder) {
        finished = builder.finished;
        products = builder.productDTOS;
    }

    public static Builder builder() {
        return new Builder();
    }


    public Boolean getFinished() {
        return finished;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }


    public static final class Builder {
        private Boolean finished;
        private List<ProductDTO> productDTOS;

        private Builder() {
        }

        public Builder withFinished(Boolean val) {
            finished = val;
            return this;
        }

        public Builder withProductDTOS(List<ProductDTO> val) {
            productDTOS = val;
            return this;
        }

        public WishlistDTO build() {
            return new WishlistDTO(this);
        }
    }
}
