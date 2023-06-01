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
                .withWishlist(getWishList(personDTO.getWishList()))
                .build();
    }

    private static Wishlist getWishList(WishlistDTO wishlist) {
        return Wishlist.builder()
                .withFinished(wishlist.getFinished())
                .withProducts(getProducts(wishlist.getProducts()))
                .build();
    }

    private static List<Product> getProducts(List<ProductDTO> products) {
        List<Product> pdcts = new ArrayList<>();
        products.forEach(productDTOas ->
                pdcts.add(Product.builder()
                        .withValue(productDTOas.getValue())
                        .withProductName(productDTOas.getProductName())
                        .withUrlImage(productDTOas.getUrlImage())
                        .build())
        );
        return pdcts;
    }
}
