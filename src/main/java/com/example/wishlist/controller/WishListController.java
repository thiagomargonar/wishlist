package com.example.wishlist.controller;

import com.example.wishlist.dto.PersonDTO;
import com.example.wishlist.service.WishListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RequestMapping(value = "/wishList")
@RestController
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @GetMapping("/{document}")
    public Mono<ResponseEntity<PersonDTO>> getWishListOfPerson(@PathVariable String document) {
        return Mono.just(document)
                .flatMap(wishListService::getWishListByPersonDocument)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
    }

    @GetMapping("/{document}/{productName}")
    public Mono<ResponseEntity<Boolean>> getExistiProductForThisPerson(@PathVariable String document, @PathVariable String productName) {
        return Mono.just(document)
                .flatMap(s -> wishListService.getExistiProductForThisPerson(document,productName))
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
    }

    @PostMapping
    public Mono<ResponseEntity<PersonDTO>> saveWishListOfPerson(@RequestBody @Valid PersonDTO personDTO) {
        return Mono.just(personDTO)
                .flatMap(wishListService::saveWishList)
                .map(p -> ResponseEntity.status(HttpStatus.CREATED).body(p));
    }

    @PutMapping
    public Mono<ResponseEntity<PersonDTO>> updateWishListOfPerson(@RequestBody @Valid PersonDTO personDTO) {
        return Mono.just(personDTO)
                .flatMap(wishListService::updateWishList)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
    }

    @DeleteMapping("/{personId}")
    public Mono<ResponseEntity<PersonDTO>> deleteWishListOfPerson(@PathVariable String personId) {
        return Mono.just(personId)
                .flatMap(wishListService::deleteWishListByPersonId)
                .map(p -> ResponseEntity.status(HttpStatus.OK).build());
    }


}
