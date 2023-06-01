package com.example.wishlist.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class WishlistDTO {

    private String id;
    private Boolean finished;

    @Size(max = 20, message = "The limit of 20 products has been exceeded, surpassing the maximum size.")
    @NotEmpty
    @Valid
    private List<ProductDTO> productDTOS;

    private WishlistDTO() {
    }

    private WishlistDTO(Builder builder) {
        id = builder.id;
        finished = builder.finished;
        productDTOS = builder.productDTOS;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public Boolean getFinished() {
        return finished;
    }

    public List<ProductDTO> getProducts() {
        return productDTOS;
    }


    public static final class Builder {
        private String id;
        private Boolean finished;
        private List<ProductDTO> productDTOS;

        private Builder() {
        }

        public Builder withId(String val) {
            id = val;
            return this;
        }

        public Builder withFinished(Boolean val) {
            finished = val;
            return this;
        }

        public Builder withProducts(List<ProductDTO> val) {
            productDTOS = val;
            return this;
        }

        public WishlistDTO build() {
            return new WishlistDTO(this);
        }
    }
}
