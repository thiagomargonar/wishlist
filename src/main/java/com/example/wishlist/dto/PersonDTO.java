package com.example.wishlist.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class PersonDTO {

    private String id;
    @NotNull
    private String nome;
    @NotNull
    private LocalDate dataNascimento;
    @NotNull
    @Valid
    private WishlistDTO wishListDTO;

    private PersonDTO() {
    }

    private PersonDTO(Builder builder) {
        id = builder.id;
        nome = builder.nome;
        dataNascimento = builder.dataNascimento;
        wishListDTO = builder.wishlistDTO;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public WishlistDTO getWishlist() {
        return wishListDTO;
    }


    public static final class Builder {
        private String id;
        private String nome;
        private LocalDate dataNascimento;
        private WishlistDTO wishlistDTO;

        private Builder() {
        }

        public Builder withId(String val) {
            id = val;
            return this;
        }

        public Builder withNome(String val) {
            nome = val;
            return this;
        }

        public Builder withDataNascimento(LocalDate val) {
            dataNascimento = val;
            return this;
        }

        public Builder withWishlist(WishlistDTO val) {
            wishlistDTO = val;
            return this;
        }

        public PersonDTO build() {
            return new PersonDTO(this);
        }
    }
}
