package com.example.wishlist.service;

import com.example.wishlist.converter.PersonConverter;
import com.example.wishlist.converter.PersonDTOConverter;
import com.example.wishlist.domain.Person;
import com.example.wishlist.dto.PersonDTO;
import com.example.wishlist.repository.WishListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.sql.SQLException;

@Service
public class WishListService {

    public static final String PROBLEMA_COM_BANCO_DE_DADOS = "Problema com banco de dados";
    private final WishListRepository wishListRepository;

    private static final Logger LOG = LoggerFactory.getLogger(WishListService.class);

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public Mono<PersonDTO> getWishListByPersonId(String personID) {
        return Mono.just(personID)
                .flatMap(wishListRepository::findById)
                .doOnNext(person -> LOG.debug("Carrinho de compra localizado com sucesso: {}", person))
                .flatMap(this::getPersonDtoByPerson)
                .doOnError(throwable -> Mono.error(new SQLException(PROBLEMA_COM_BANCO_DE_DADOS, throwable)));
    }

    public Mono<PersonDTO>saveWishList(PersonDTO personDTO) {
        return Mono.just(personDTO)
                .map(PersonConverter::ofPersonDTO)
                .flatMap(wishListRepository::save)
                .doOnNext(person -> LOG.debug("Carrinho de compra salvo com sucesso: {}", person))
                .flatMap(this::getPersonDtoByPerson)
                .doOnError(throwable -> Mono.error(new SQLException(PROBLEMA_COM_BANCO_DE_DADOS, throwable)));
    }

    public Mono<PersonDTO>updateWishList(PersonDTO personDTO) {
        return Mono.just(personDTO)
                .map(PersonConverter::ofPersonDTO)
                .flatMap(wishListRepository::updateWishList)
                .doOnNext(l -> LOG.debug("Carrinho de compra atualizado com sucesso: {}", l))
                .flatMap(this::getPersonDtoByPerson);
    }

    public Mono<Void> deleteWishListByPersonId(String personID) {
        return Mono.just(personID)
                .flatMap(wishListRepository::deleteById)
                .doOnNext(unused -> LOG.debug("Carrinho de compra deletado com sucesso: {}", personID))
                .doOnError(throwable -> Mono.error(new SQLException(PROBLEMA_COM_BANCO_DE_DADOS, throwable)))
                .then();
    }

    private Mono<PersonDTO> getPersonDtoByPerson(Person person) {
        return Mono.just(person)
                .map(PersonDTOConverter::ofPerson);
    }
}