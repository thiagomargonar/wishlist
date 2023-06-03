package com.example.wishlist.message;


import com.example.wishlist.dto.PersonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Component
public class DreamProductProducer implements FinishSaleSend {
    private static final Logger logger = LoggerFactory.getLogger(DreamProductProducer.class);

    private final Sinks.Many<PersonDTO> sink;

    public DreamProductProducer() {
        this.sink = Sinks.many().unicast().onBackpressureBuffer();
    }


    @Bean
    public Supplier<Flux<PersonDTO>> dreamProductReturn() {
        return () -> sink.asFlux()
                .doOnNext(m -> logger.debug("Sending creation account request message {}", m))
                .doOnError(t -> logger.error("Error sending creation account request message", t));
    }

    @Bean
    public Supplier<Flux<PersonDTO>> dreamProduct() {
        return () -> sink.asFlux()
                .doOnNext(m -> logger.debug("Sending cash with request document {}", m))
                .doOnError(t -> logger.error("Error sending cash account request message: ", t));
    }

    @Override
    public Mono<PersonDTO> sendMessageOf(PersonDTO request) {
        return Mono.just(sink.tryEmitNext(request))
                .doOnNext(emitResult -> logger.info("emiti result IN: {}", emitResult))
                .thenReturn(request);
    }
}
