package com.example.wishlist.repository;

import com.example.wishlist.domain.Person;
import reactor.core.publisher.Mono;

public interface WishListRepositoryCustom {
    Mono<Person> updateWishList(Person person);
}
