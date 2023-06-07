package com.example.wishlist.domain;

import com.example.wishlist.annotations.CpfOrCnpj;
import com.example.wishlist.annotations.MaxWishlist;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "person")
@TypeAlias(value = "person")
public class Person {
    @Id
    private String id;

    @NotNull
    private String name;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    @CpfOrCnpj
    private String document;

    @NotEmpty
    @MaxWishlist
    private List<Wishlist> wishlist;

    private Person() {
    }

    private Person(Builder builder) {
        id = builder.id;
        name = builder.nome;
        birthDate = builder.dataNascimento;
        document = builder.document;
        wishlist = builder.wishlist;
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

    public String getDocument() {
        return document;
    }

    public List<Wishlist> getWishlist() {
        return wishlist;
    }

    public static final class Builder {
        private String id;
        private String nome;
        private LocalDate dataNascimento;
        private String document;
        private List<Wishlist> wishlist;

        private Builder() {
        }

        public Builder withId(String val) {
            id = val;
            return this;
        }

        public Builder withWishlist(List<Wishlist> val) {
            wishlist = val;
            return this;
        }

        public Builder withDocument(String val) {
            document = val;
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

        public Person build() {
            return new Person(this);
        }
    }
}
