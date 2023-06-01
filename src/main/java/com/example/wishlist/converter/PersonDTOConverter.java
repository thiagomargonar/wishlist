package com.example.wishlist.converter;

import com.example.wishlist.domain.Person;
import com.example.wishlist.domain.Product;
import com.example.wishlist.domain.Wishlist;
import com.example.wishlist.dto.PersonDTO;
import com.example.wishlist.dto.ProductDTO;
import com.example.wishlist.dto.WishlistDTO;

import java.util.ArrayList;
import java.util.List;

public class PersonDTOConverter {

    private PersonDTOConverter() {
    }

    public static PersonDTO ofPerson(Person person) {
        return PersonDTO.builder()
                .withDataNascimento(person.getDataNascimento())
                .withId(person.getId())
                .withNome(person.getNome())
                .withWishList(getWishList(person.getWishList()))
                .build();
    }

    private static WishlistDTO getWishList(Wishlist wishlist) {
        return WishlistDTO.builder()
                .withFinished(wishlist.getFinished())
                .withProductDTOS(getProducts(wishlist.getProducts()))
                .build();
    }

    private static List<ProductDTO> getProducts(List<Product> products) {
        List<ProductDTO> pdcts = new ArrayList<>();
        products.forEach(productDTO ->
                pdcts.add(ProductDTO.builder()
                        .withValue(productDTO.getValue())
                        .withProductName(productDTO.getProductName())
                        .withUrlImage(productDTO.getUrlImage())
                        .build())
        );
        return pdcts;
    }
}
