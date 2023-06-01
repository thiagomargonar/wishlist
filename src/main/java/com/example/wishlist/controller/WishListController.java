package com.example.wishlist.controller;

import com.example.wishlist.dto.PersonDTO;
import com.example.wishlist.service.WishListService;
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

    @GetMapping("/{personId}")
    public Mono<ResponseEntity<PersonDTO>> getWishListOfPerson(@PathVariable String personId) {
        return Mono.just(personId)
                .flatMap(wishListService::getWishListByPersonId)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/save")
    public Mono<ResponseEntity<PersonDTO>> saveWishListOfPerson(@RequestBody PersonDTO personDTO) {
        return Mono.just(personDTO)
                //.flatMap(wishListService::saveWishList)
                //.map(ResponseEntity::ok);
                .thenReturn(ResponseEntity.ok(personDTO));
    }

    @PutMapping("/update")
    public Mono<ResponseEntity<PersonDTO>> updateWishListOfPerson(@RequestBody @Valid PersonDTO personDTOas) {
        return Mono.just(personDTOas)
                .flatMap(wishListService::updateWishList)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{personId}")
    public Mono<ResponseEntity<PersonDTO>> deleteWishListOfPerson(@PathVariable String personId) {
        return Mono.just(personId)
                .flatMap(wishListService::deleteWishListByPersonId)
                .map(unused -> ResponseEntity.noContent().build());
    }


}
