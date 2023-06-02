package com.example.wishlist.service;

import com.example.wishlist.converter.PersonConverter;
import com.example.wishlist.converter.PersonDTOConverter;
import com.example.wishlist.domain.Person;
import com.example.wishlist.dto.PersonDTO;
import com.example.wishlist.message.FinishedResolvedProducer;
import com.example.wishlist.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.sql.SQLDataException;
import java.sql.SQLException;

@Service
public class WishListService {

    public static final String PROBLEMA_COM_BANCO_DE_DADOS = "Problema com banco de dados";
    private final PersonRepository personRepository;
    private final FinishedResolvedProducer finishedResolvedProducer;
    private static final Logger LOG = LoggerFactory.getLogger(WishListService.class);

    public WishListService(PersonRepository personRepository, FinishedResolvedProducer finishedResolvedProducer) {
        this.personRepository = personRepository;
        this.finishedResolvedProducer = finishedResolvedProducer;
    }

    public Mono<PersonDTO> getWishListByPersonDocument(String document) {
        return Mono.just(document)
                .flatMap(personRepository::findByDocument)
                .doOnNext(person -> LOG.debug("Carrinho de compra localizado com sucesso: {}", person))
                .flatMap(this::getPersonDtoByPerson)
                .doOnError(throwable -> Mono.error(new SQLException(PROBLEMA_COM_BANCO_DE_DADOS, throwable)));
    }

    public Mono<PersonDTO> saveWishList(PersonDTO personDTO) {
        return Mono.just(personDTO)
                .map(PersonConverter::ofPersonDTO)
                .flatMap(this::validatePerson)
                .flatMap(personRepository::save)
                .doOnNext(person -> LOG.debug("Carrinho de compra salvo com sucesso: {}", person))
                .flatMap(this::getPersonDtoByPerson);
    }

    private Mono<Person> validatePerson(Person pDTO) {
        if (personRepository.findByDocument(pDTO.getDocument())
                .blockOptional().isPresent()) {
            return Mono.error(new SQLDataException("Já existe um documento com carrinho ativo;"));
        }

        return Mono.just(pDTO);
    }


    public Mono<PersonDTO> updateWishList(PersonDTO personDTO) {
        return Mono.just(personDTO)
                .map(PersonConverter::ofPersonDTO)
                .flatMap(personRepository::updateWishList)
                .flatMap(person -> getWishListByPersonDocument(person.getDocument()))
                .doOnNext(l -> LOG.debug("Carrinho de compra atualizado com sucesso: {}", l));
    }

    public Mono<Void> deleteWishListByPersonId(String personID) {
        return Mono.just(personID)
                .flatMap(personRepository::deleteById)
                .doOnNext(unused -> LOG.debug("Carrinho de compra deletado com sucesso: {}", personID))
                .doOnError(throwable -> Mono.error(new SQLException(PROBLEMA_COM_BANCO_DE_DADOS, throwable)))
                .then();
    }

    private Mono<PersonDTO> getPersonDtoByPerson(Person person) {
        return Mono.just(person)
                .map(PersonDTOConverter::ofPerson);
    }

    public Mono<Void> finishedWishList(String document) {
        return Mono.just(document)
                .flatMap(this::getWishListByPersonDocument)
                .flatMap(finishedResolvedProducer::sendMessageToTopics)
                .doOnNext(dto -> LOG.debug("Envio de mensagens finalizadas solicitadas: {}", dto))
                .flatMap(personDTO1 ->  deleteWishListByPersonId(personDTO1.getId()))
                .then();
    }
}
