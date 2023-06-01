package com.example.wishlist.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
@Document(collection = "person")
@TypeAlias(value = "person")
public class Person {
    @Id
    private String id;
    @NotNull
    private String nome;
    @NotNull
    private LocalDate dataNascimento;

    @NotNull
    @Valid
    private Wishlist wishlist;

    private Person() {
    }

    private Person(Builder builder) {
        id = builder.id;
        nome = builder.nome;
        dataNascimento = builder.dataNascimento;
        wishlist = builder.wishlist;
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

    public Wishlist getWishlist() {
        return wishlist;
    }


    public static final class Builder {
        private String id;
        private String nome;
        private LocalDate dataNascimento;
        private Wishlist wishlist;

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

        public Builder withWishlist(Wishlist val) {
            wishlist = val;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }
}
