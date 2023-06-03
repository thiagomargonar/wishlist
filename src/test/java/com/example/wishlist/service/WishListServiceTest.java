package com.example.wishlist.service;

import com.example.wishlist.converter.PersonConverter;
import com.example.wishlist.converter.PersonDTOConverter;
import com.example.wishlist.domain.Person;
import com.example.wishlist.domain.Wishlist;
import com.example.wishlist.dto.PersonDTO;
import com.example.wishlist.dto.WishlistDTO;
import com.example.wishlist.message.FinishedResolvedProducer;
import com.example.wishlist.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WishListServiceTest {

    @Mock
    private PersonRepository personRepository;
    @Mock
    private FinishedResolvedProducer finishedResolvedProducer;

    @InjectMocks
    private WishListService wishListService;

    @Test
    void when_call_getExistiProductForThisPerson_then_return_if_product_exist() {
        var person = Person.builder()
                .withDataNascimento(LocalDate.now())
                .withDocument("12345678901")
                .withNome("Test de Integração")
                .withWishlist(getWishList())
                .build();
        when(personRepository.existsByDocumentAndProductName(person.getDocument(), person.getWishlist().get(0).getProductName())).thenReturn(Mono.just(true));

        StepVerifier.create(wishListService.getExistiProductForThisPerson(person.getDocument(), person.getWishlist().get(0).getProductName()))
                .assertNext(result -> assertThat(result).isTrue())
                .expectComplete()
                .verify();

        verify(personRepository).existsByDocumentAndProductName(anyString(), anyString());
    }

    @Test
    void when_call_getWishListByPersonDocument_then_return_a_person() {
        var person = Person.builder()
                .withDataNascimento(LocalDate.now())
                .withDocument("12345678901")
                .withNome("Test de Integração")
                .withWishlist(getWishList())
                .build();
        when(personRepository.findByDocument(person.getDocument())).thenReturn(Mono.just(person));

        StepVerifier.create(wishListService.getWishListByPersonDocument(person.getDocument()))
                .assertNext(result -> assertThat(result.getDocument()).isEqualTo(person.getDocument()))
                .expectComplete()
                .verify();

        verify(personRepository).findByDocument(any());
    }

    @Test
    void when_call_getWishListByPersonDocument_and_database_be_problem_then_return_a_SQLException() {
        var person = Person.builder()
                .withDataNascimento(LocalDate.now())
                .withDocument("12345678901")
                .withNome("Test de Integração")
                .withWishlist(getWishList())
                .build();
        when(personRepository.findByDocument(person.getDocument())).thenReturn(Mono.error(new SQLException()));

        StepVerifier.create(wishListService.getWishListByPersonDocument(person.getDocument()))
                .expectErrorSatisfies(error ->  assertThat(error.getClass()).isEqualTo(SQLException.class))
                .verify();

        verify(personRepository).findByDocument(any());
    }

    @Test
    void when_call_saveWishList_then_return_a_Mono_of_person() {
        var person = Person.builder()
                .withDataNascimento(LocalDate.now())
                .withDocument("12345678901")
                .withNome("Test de Integração")
                .withWishlist(getWishList())
                .build();

        var personDto = PersonDTOConverter.ofPerson(person);

        when(personRepository.findByDocument(person.getDocument())).thenReturn(Mono.justOrEmpty(Optional.empty()));
        when(personRepository.save(any())).thenReturn(Mono.just(person));

        StepVerifier.create(wishListService.saveWishList(personDto))
                .assertNext(result -> assertThat(result.getDocument()).isEqualTo(person.getDocument()))
                .expectComplete()
                .verify();

        verify(personRepository).findByDocument(any());
        verify(personRepository).save(any());
    }

    @Test
    void when_call_saveWishList_but_exist_a_person_then_return_a_exception() {
        var person = Person.builder()
                .withDataNascimento(LocalDate.now())
                .withDocument("12345678901")
                .withNome("Test de Integração")
                .withWishlist(getWishList())
                .build();

        var personDto = PersonDTOConverter.ofPerson(person);

        when(personRepository.findByDocument(person.getDocument())).thenReturn(Mono.just(person));

        StepVerifier.create(wishListService.saveWishList(personDto))
                .expectErrorSatisfies(error -> {
                    assertThat(error.getClass()).isEqualTo(SQLDataException.class);
                    assertThat(error.getMessage()).isEqualTo("Já existe um documento com carrinho ativo;");
                })
                .verify();

        verify(personRepository).findByDocument(any());
        verify(personRepository, never()).save(any());
    }

    @Test
    void when_call_updateWishList_then_return_a_person_update() {
        var personUpdate =Person.builder()
                .withDataNascimento(LocalDate.now())
                .withDocument("12345678901")
                .withNome("Test de Integração")
                .withWishlist(getWishListToUpdate())
                .build();

        var personDTO = PersonDTOConverter.ofPerson(personUpdate);

        when(personRepository.updateWishList(any())).thenReturn(Mono.just(personUpdate));
        when(personRepository.findByDocument(personUpdate.getDocument())).thenReturn(Mono.just(personUpdate));

        StepVerifier.create(wishListService.updateWishList(personDTO))
                .assertNext(result -> assertThat(result.getDocument())
                        .isEqualTo(personUpdate.getDocument()))
                .expectComplete()
                .verify();

        verify(personRepository).findByDocument(any());
        verify(personRepository).updateWishList(any());
    }

    @Test
    void when_call_deleteWishListByPersonId_then_return_a_empty() {

        when(personRepository.deleteById(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(wishListService.deleteWishListByPersonId("personDTO"))
                .expectComplete()
                .verify();

        verify(personRepository).deleteById(anyString());
    }

    @Test
    void when_call_finishedWishList_then_send_a_message_to_facade() {

        var pDTO = PersonDTO.builder()
                .withId("123456")
                .withDataNascimento(LocalDate.now())
                .withWishDocument("12345678901")
                .withNome("Test de Integração")
                .withWishList(getWishListDTOToUpdate())
                .build();

        when(personRepository.deleteById(anyString())).thenReturn(Mono.empty());
        when(personRepository.findByDocument(anyString())).thenReturn(Mono.just(PersonConverter.ofPersonDTO(pDTO)));

        when(finishedResolvedProducer.sendMessageToTopics(any())).thenReturn(Mono.just(pDTO));

        StepVerifier.create(wishListService.finishedWishList(anyString()))
                .expectComplete()
                .verify();

        verify(finishedResolvedProducer).sendMessageToTopics(any());
        verify(personRepository).deleteById(anyString());
    }

    private List<Wishlist> getWishList() {
        return List.of(Wishlist.builder()
                .withValue(BigDecimal.ONE)
                .withProductName("produto de teste")
                .withUrlImage("/teste/123")
                .build());
    }

    private List<Wishlist> getWishListToUpdate() {
        return List.of(Wishlist.builder()
                .withValue(BigDecimal.ONE)
                .withProductName("teste de produto")
                .withUrlImage("/teste/123")
                .build());
    }

    private List<WishlistDTO> getWishListDTOToUpdate() {
        return List.of(WishlistDTO.builder()
                .withValue(BigDecimal.ONE)
                .withProductName("teste de produto")
                .withUrlImage("/teste/123")
                .build());
    }
}