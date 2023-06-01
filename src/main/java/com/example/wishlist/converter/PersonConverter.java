package com.example.wishlist.converter;

import com.example.wishlist.domain.Person;
import com.example.wishlist.domain.Product;
import com.example.wishlist.domain.Wishlist;
import com.example.wishlist.dto.PersonDTO;
import com.example.wishlist.dto.ProductDTO;
import com.example.wishlist.dto.WishlistDTO;

import java.util.ArrayList;
import java.util.List;

public class PersonConverter {
    private PersonConverter() {
    }

    public static Person ofPersonDTO(PersonDTO personDTO) {
        return Person.builder()
                .withDataNascimento(personDTO.getDataNascimento())
                .withId(personDTO.getId())
                .withNome(personDTO.getNome())
                .withWishlist(getWishList(personDTO.getWishlist()))
                .build();
    }

    private static Wishlist getWishList(WishlistDTO wishlist) {
        return Wishlist.builder()
                .withFinished(wishlist.getFinished())
                .withId(wishlist.getId())
                .withProducts(getProducts(wishlist.getProducts()))
                .build();
    }

    private static List<Product> getProducts(List<ProductDTO> products) {
        List<Product> pdcts = new ArrayList<>();
        products.forEach(productDTO ->
                pdcts.add(Product.builder()
                        .withValue(productDTO.getValue())
                        .withId(productDTO.getId())
                        .withProductName(productDTO.getProductName())
                        .withUrlImage(productDTO.getUrlImage())
                        .build())
        );
        return pdcts;
    }
}
