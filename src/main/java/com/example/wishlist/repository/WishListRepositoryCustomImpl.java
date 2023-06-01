package com.example.wishlist.repository;

import com.example.wishlist.domain.Person;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class WishListRepositoryCustomImpl implements WishListRepositoryCustom{


    public static final String WISH_LIST = "wishlist";
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public WishListRepositoryCustomImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Mono<Person> updateWishList(Person person) {
        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(Criteria.where("document").is(person.getDocument()));
        criteriaList.forEach(query::addCriteria);

        Update update = new Update()
                .set(WISH_LIST, person.getWishlist());
        return reactiveMongoTemplate.findAndModify(query, update, Person.class);
    }
}
