package com.example.wishlist.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class PersonDTO {

    private String id;

    private String nome;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dataNascimento;

    private WishlistDTO wishList;

    private PersonDTO() {
    }

    private PersonDTO(Builder builder) {
        id = builder.id;
        nome = builder.nome;
        dataNascimento = builder.dataNascimento;
        wishList = builder.wishList;
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

    public WishlistDTO getWishList() {
        return wishList;
    }


    public static final class Builder {
        private String id;
        private String nome;
        private LocalDate dataNascimento;
        private WishlistDTO wishList;

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

        public Builder withWishList(WishlistDTO val) {
            wishList = val;
            return this;
        }

        public PersonDTO build() {
            return new PersonDTO(this);
        }
    }
}
