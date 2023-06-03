package com.example.wishlist.message;

import com.example.wishlist.service.WishListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.function.Consumer;

@Service
public class WishListCompletedConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WishListCompletedConsumer.class);
    private static final String RETRY_MESSAGE = "Erro ao consumir o pagamento: {}, Retry: {}";
    private final Integer maxRetries;
    private final Duration minBackoff;
    private final Duration maxBackoff;
    private final Duration blockTimeout;

    private final WishListService wishListService;

    public WishListCompletedConsumer(@Value("${application.consumer.retry.maxRetries:3}") int maxRetries,
                                     @Value("${application.consumer.retry.initialInterval:5000}") int minBackoff,
                                     @Value("${application.consumer.retry.maxInterval:20000}") long maxBackoff,
                                     @Value("${application.consumer.block:300000}") long maxBlockDuration,
                                     WishListService wishListService) {
        this.maxRetries = maxRetries;
        this.minBackoff = Duration.ofMillis(minBackoff);
        this.maxBackoff = Duration.ofMillis(maxBackoff);
        this.blockTimeout = Duration.ofMillis(maxBlockDuration);
        this.wishListService = wishListService;
    }


    @Bean
    public Consumer<String> wishListFinishedConsumerReturn() {
        return s -> Mono.just(s)
                .doOnNext(str -> LOGGER.debug("Consumer message of wishList, message: {}", str))
                .flatMap(wishListService::finishedWishList)
                .retryWhen(Retry
                        .backoff(maxRetries, minBackoff)
                        .maxBackoff(maxBackoff)
                        .doAfterRetry(it -> LOGGER.info(RETRY_MESSAGE, s, it))
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> retrySignal.failure()))
                .onErrorResume(t -> processRetryExhausted(t, s))
                .block(blockTimeout);
    }

    private Mono<Void> processRetryExhausted(Throwable throwable, String request) {
        return Mono.just(throwable)
                .doOnNext(p -> LOGGER.debug("Error of try to : {} with erro: {}", request, p.getMessage()))
                .then();
    }
}
