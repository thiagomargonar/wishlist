package com.example.wishlist.domain;

import org.springframework.data.annotation.Id;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class Wishlist {
    private Boolean finished;

    @Size(max=20, message = "The limit of 20 products has been exceeded, surpassing the maximum size.")
    @NotEmpty
    @Valid
    private List<Product> products;

    private Wishlist() {
    }

    private Wishlist(Builder builder) {
        finished = builder.finished;
        products = builder.products;
    }

    public static Builder builder() {
        return new Builder();
    }


    public Boolean getFinished() {
        return finished;
    }

    public List<Product> getProducts() {
        return products;
    }


    public static final class Builder {
        private Boolean finished;
        private List<Product> products;

        private Builder() {
        }
        public Builder withFinished(Boolean val) {
            finished = val;
            return this;
        }

        public Builder withProducts(List<Product> val) {
            products = val;
            return this;
        }

        public Wishlist build() {
            return new Wishlist(this);
        }
    }
}
