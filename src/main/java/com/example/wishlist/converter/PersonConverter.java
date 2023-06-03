package com.example.wishlist.converter;

import com.example.wishlist.domain.Person;
import com.example.wishlist.dto.PersonDTO;
import com.example.wishlist.dto.WishlistDTO;

import java.util.ArrayList;
import java.util.List;

public class PersonConverter {
    private PersonConverter() {
    }

    public static Person ofPersonDTO(PersonDTO personDTO) {
        return Person.builder()
                .withDataNascimento(personDTO.getBirthDate())
                .withId(personDTO.getId())
                .withDocument(personDTO.getDocument())
                .withWishlist(getProducts(personDTO.getWishList()))
                .withNome(personDTO.getName())
                .build();
    }

    private static List<com.example.wishlist.domain.Wishlist> getProducts(List<WishlistDTO> products) {
        List<com.example.wishlist.domain.Wishlist> pdcts = new ArrayList<>();
        products.forEach(productDTOas ->
                pdcts.add(com.example.wishlist.domain.Wishlist.builder()
                        .withValue(productDTOas.getValue())
                        .withProductName(productDTOas.getProductName())
                        .withUrlImage(productDTOas.getUrlImage())
                        .build())
        );
        return pdcts;
    }
}
