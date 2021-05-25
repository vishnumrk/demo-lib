package io.ms.lib.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CompletableFuture;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Component
public class WebClientConfig {

    private static final Logger log = LoggerFactory.getLogger(WebClientConfig.class);
    private final WebClient.Builder webClientBuilder;

    public WebClientConfig(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Bean
    public WebClient webClient() {
        return webClientBuilder
                .filter(new CorrelationIdInjectorFilterFunction()
                        .andThen(logRequest())
                        .andThen(logResponse()))
                .build();
    }


    // This method returns filter function which will log request data
    private static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url(), kv("headers", clientRequest.headers().toSingleValueMap()));
            return Mono.just(clientRequest);
        });
    }

    private static ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.info("Response Headers : ", kv("headers", clientResponse.headers()));

            final CompletableFuture<ClientResponse> future = new CompletableFuture<>();
            clientResponse.body(BodyExtractors.toMono(String.class))
                    .subscribeOn(Schedulers.boundedElastic())
                    .doOnNext(s -> log.info("Response Body : ", kv("payload", s)))
                    .subscribe(s -> {
                        final ClientResponse response = ClientResponse.create(clientResponse.statusCode())
                                .body(s)
                                .headers(httpHeaders -> httpHeaders.addAll(clientResponse.headers().asHttpHeaders()))
                                .build();
                        future.complete(response);
                    });

            return Mono.fromFuture(future);
        });
    }

}