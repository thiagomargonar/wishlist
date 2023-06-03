package com.example.wishlist.repository;

import com.example.wishlist.domain.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PersonRepository extends ReactiveMongoRepository<Person, String>, WishListRepositoryCustom {

    Mono<Person> findByDocument(String document);

}
