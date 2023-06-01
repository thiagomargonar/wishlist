package com.example.wishlist.converter;

import com.example.wishlist.domain.Person;
import com.example.wishlist.dto.PersonDTO;
import com.example.wishlist.dto.WishlistDTO;

import java.util.ArrayList;
import java.util.List;

public class PersonDTOConverter {

    private PersonDTOConverter() {
    }

    public static PersonDTO ofPerson(Person person) {
        return PersonDTO.builder()
                .withId(person.getId())
                .withWishDocument(person.getDocument())
                .withDataNascimento(person.getDataNascimento())
                .withNome(person.getNome())
                .withWishList(getProducts(person.getWishlist()))
                .build();
    }

    private static List<WishlistDTO> getProducts(List<com.example.wishlist.domain.Wishlist> wishlists) {
        List<WishlistDTO> pdcts = new ArrayList<>();
        wishlists.forEach(wishlistDTO ->
                pdcts.add(WishlistDTO.builder()
                        .withValue(wishlistDTO.getValue())
                        .withProductName(wishlistDTO.getProductName())
                        .withUrlImage(wishlistDTO.getUrlImage())
                        .build())
        );
        return pdcts;
    }
}
