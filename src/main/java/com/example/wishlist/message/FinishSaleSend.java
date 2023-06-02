package com.example.wishlist.message;

import com.example.wishlist.dto.PersonDTO;
import reactor.core.publisher.Mono;

public interface FinishSaleSend {
    Mono<PersonDTO> sendMessageOf(PersonDTO person);

}
