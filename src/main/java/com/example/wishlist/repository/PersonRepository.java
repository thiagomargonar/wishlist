package com.example.wishlist.repository;

import com.example.wishlist.domain.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends ReactiveMongoRepository<Person, String>, WishListRepositoryCustom {

}
