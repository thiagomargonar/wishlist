package com.example.wishlist.repository;

import com.example.wishlist.domain.Person;
import com.example.wishlist.domain.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DataMongoTest(includeFilters = @ComponentScan.Filter(Repository.class))
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class WishListRepositoryCustomImplTest {

    @Autowired
    private PersonRepository repository;

    @BeforeEach
    public void setUp() {
        repository.deleteAll().block();
    }

    @Test
    void when_updateWishList_then_return_Person_With_value_wishList_update() {
        var person = Person.builder()
                .withDataNascimento(LocalDate.now())
                .withDocument("12345678901")
                .withNome("Test de Integração")
                .withWishlist(getWishList())
                .build();

        repository.save(person).block();

        var personUpdate = Person.builder()
                .withDataNascimento(LocalDate.now())
                .withDocument("12345678901")
                .withNome("Test de Integração")
                .withWishlist(getWishList2())
                .build();

        var listPaymentAccount = repository.findAll().collectList().block();
        assert listPaymentAccount != null;
        assertThat(listPaymentAccount).isNotEmpty();
        assertThat(listPaymentAccount.get(0).getDocument()).isEqualTo(person.getDocument());
        assertThat(listPaymentAccount.get(0).getWishlist()).hasSize(3);

        repository.updateWishList(personUpdate).block();
        var listPaymentAccountUpdate = repository.findAll().collectList().block();
        assert listPaymentAccountUpdate != null;
        assertThat(listPaymentAccountUpdate).isNotEmpty();
        assertThat(listPaymentAccountUpdate.get(0).getDocument()).isEqualTo(personUpdate.getDocument());
        assertThat(listPaymentAccountUpdate.get(0).getWishlist()).hasSize(1);
    }


    @Test
    void when_need_looking_if_exist_product_by_person_then_return_if_product_exist() {
        var person = Person.builder()
                .withDataNascimento(LocalDate.now())
                .withDocument("12345678901")
                .withNome("Test de Integração")
                .withWishlist(getWishList())
                .build();

        repository.save(person).block();

        var listPaymentAccount = repository.findAll().collectList().block();
        assert listPaymentAccount != null;
        assertThat(listPaymentAccount).isNotEmpty();
        assertThat(listPaymentAccount.get(0).getDocument()).isEqualTo(person.getDocument());
        assertThat(listPaymentAccount.get(0).getWishlist()).hasSize(3);

        var exit = repository.existsByDocumentAndProductName(person.getDocument(),"produto de teste").block();

        assertThat(exit).isTrue();

        exit = repository.existsByDocumentAndProductName(person.getDocument(),"produto inexistente").block();
        assertThat(exit).isFalse();

    }

    private List<Wishlist> getWishList2() {
        return List.of(Wishlist.builder()
                .withValue(BigDecimal.ONE)
                .withProductName("produto de teste Atualizado")
                .withUrlImage("/teste/123")
                .build());
    }

    private List<Wishlist> getWishList() {
        return List.of(Wishlist.builder()
                        .withValue(BigDecimal.ONE)
                        .withProductName("produto de teste")
                        .withUrlImage("/teste/123")
                        .build(),
                Wishlist.builder()
                        .withValue(BigDecimal.ONE)
                        .withProductName("produto de teste2")
                        .withUrlImage("/teste/123")
                        .build(),
                Wishlist.builder()
                        .withValue(BigDecimal.ONE)
                        .withProductName("produto de teste3")
                        .withUrlImage("/teste/123")
                        .build());
    }

}