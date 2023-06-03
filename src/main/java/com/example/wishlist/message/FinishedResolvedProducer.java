package com.example.wishlist.message;

import com.example.wishlist.dto.PersonDTO;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class FinishedResolvedProducer {

    private final List<FinishSaleSend> finishSaleSend;

    public FinishedResolvedProducer(List<FinishSaleSend> finishSaleSend) {
        this.finishSaleSend = finishSaleSend;
    }

    public Mono<PersonDTO> sendMessageToTopics(PersonDTO personDTO) {
        return Mono.just(personDTO)
                .map(this::sendMessageTo);

    }

    private PersonDTO sendMessageTo(PersonDTO p) {
        finishSaleSend.forEach(finishSale -> {
            finishSale.sendMessageOf(p);
        });
        return p;
    }


}
