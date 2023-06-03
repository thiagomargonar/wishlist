package com.example.wishlist.message;

import com.example.wishlist.dto.PersonDTO;
import com.example.wishlist.service.WishListService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WishListCompletedConsumerTest {

    @Mock
    private WishListService wishListService;

    private WishListCompletedConsumer wishListCompletedConsumer;

    @BeforeEach
    void setUp() {
        wishListCompletedConsumer = new WishListCompletedConsumer(
                3,
                5000,
                20000,
                300000,
                wishListService);
    }

    @Test
    void when_sendMessageOf_then_send_message_to_topic() {

        var pDTO = PersonDTO.builder()
                .withId("123456")
                .withDataNascimento(LocalDate.now())
                .withWishDocument("12345678901")
                .withNome("Test de Integração")
                .build();

        when(wishListService.finishedWishList(pDTO.getDocument())).thenReturn(Mono.empty());
        Assertions.assertDoesNotThrow(() -> wishListCompletedConsumer.wishListFinishedConsumerReturn().accept(pDTO.getDocument()));
    }

    @Test
    void when_sendMessageOf_then_send_message_to_topic1() {

        var pDTO = PersonDTO.builder()
                .withId("123456")
                .withDataNascimento(LocalDate.now())
                .withWishDocument("12345678901")
                .withNome("Test de Integração")
                .build();

        when(wishListService.finishedWishList(pDTO.getDocument())).thenReturn(Mono.empty());
        Assertions.assertDoesNotThrow(() -> wishListCompletedConsumer.wishListFinishedConsumerReturn().accept(pDTO.getDocument()));
    }

}