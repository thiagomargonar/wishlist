package com.example.wishlist.message;

import com.example.wishlist.dto.PersonDTO;
import com.example.wishlist.dto.WishlistDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DreamProductProducerTest {


    @Mock
    private Sinks.Many<PersonDTO> personDTOMany;

    @InjectMocks
    private DreamProductProducer dreamProductProducer;

    @Test
    void when_sendMessageOf_then_send_message_to_topic() {

        var pDTO = PersonDTO.builder()
                .withId("123456")
                .withDataNascimento(LocalDate.now())
                .withWishDocument("12345678901")
                .withNome("Test de Integração")
                .withWishList(getWishListDTOToUpdate())
                .build();

        StepVerifier
                .create(dreamProductProducer.sendMessageOf(pDTO))
                .assertNext(result -> assertThat(result).usingRecursiveComparison().isEqualTo(pDTO))
                .expectComplete()
                .verify();
    }

    private List<WishlistDTO> getWishListDTOToUpdate() {
        return List.of(WishlistDTO.builder()
                .withValue(BigDecimal.ONE)
                .withProductName("teste de produto")
                .withUrlImage("/teste/123")
                .build());
    }


}