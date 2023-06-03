package com.example.wishlist.dto;

import com.example.wishlist.annotations.CpfOrCnpj;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class PersonDTO {

    private String id;

    @NotNull
    private String name;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    @NotNull
    @CpfOrCnpj
    private String document;
    @NotEmpty
    private List<WishlistDTO> wishList;

    private PersonDTO() {
    }

    private PersonDTO(Builder builder) {
        id = builder.id;
        name = builder.nome;
        birthDate = builder.dataNascimento;
        document = builder.document;
        wishList = builder.wishList;
    }

    public String getDocument() {
        return document;
    }

    public List<WishlistDTO> getWishList() {
        if(wishList.size() > 20){
            throw new RuntimeException("Numero maximo sugerido na lista é maior que 20");
        }
        return wishList;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
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
