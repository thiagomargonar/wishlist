package com.example.wishlist.dto;

import com.example.wishlist.annotations.CpfOrCnpj;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class PersonDTO {

    private String id;

    private String nome;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dataNascimento;

    @NotNull
    @CpfOrCnpj
    private String document;

    private List<WishlistDTO> wishList;

    private PersonDTO() {
    }

    private PersonDTO(Builder builder) {
        id = builder.id;
        nome = builder.nome;
        dataNascimento = builder.dataNascimento;
        document = builder.document;
        wishList = builder.wishList;
    }

    public String getDocument() {
        return document;
    }

    public List<WishlistDTO> getWishList() {
        return wishList;
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

    public static final class Builder {
        private String id;
        private String nome;
        private LocalDate dataNascimento;
        private List<WishlistDTO> wishList;
        private String document;

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

        public Builder withWishList(List<WishlistDTO> val) {
            wishList = val;
            return this;
        }

        public Builder withDataNascimento(LocalDate val) {
            dataNascimento = val;
            return this;
        }

        public Builder withWishDocument(String val) {
            document = val;
            return this;
        }

        public PersonDTO build() {
            return new PersonDTO(this);
        }
    }
}
