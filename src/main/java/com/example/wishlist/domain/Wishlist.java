package com.example.wishlist.domain;

import org.springframework.data.annotation.Id;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class Wishlist {
    @Id
    private String id;
    private Boolean finished;

    @Size(max=20, message = "The limit of 20 products has been exceeded, surpassing the maximum size.")
    @NotEmpty
    @Valid
    private List<Product> products;

    private Wishlist() {
    }

    private Wishlist(Builder builder) {
        id = builder.id;
        finished = builder.finished;
        products = builder.products;
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

    public List<Product> getProducts() {
        return products;
    }


    public static final class Builder {
        private String id;
        private Boolean finished;
        private List<Product> products;

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

        public Builder withProducts(List<Product> val) {
            products = val;
            return this;
        }

        public Wishlist build() {
            return new Wishlist(this);
        }
    }
}
