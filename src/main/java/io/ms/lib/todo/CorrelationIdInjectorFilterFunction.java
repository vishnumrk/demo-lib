package io.ms.lib.todo;

import io.ms.lib.RequestCorrelationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.sleuth.http.HttpClientRequest;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

import java.util.Collection;

public class CorrelationIdInjectorFilterFunction implements ExchangeFilterFunction {

    private static final Logger log = LoggerFactory.getLogger(CorrelationIdInjectorFilterFunction.class);

    @Override
    public Mono<ClientResponse> filter(ClientRequest clientRequest, ExchangeFunction exchangeFunction) {
        final ClientRequestWrapper wrapper = new ClientRequestWrapper(clientRequest);
        final String correlationId = RequestCorrelationContext.getCurrent().getCorrelationId();
        log.trace("Correlation Id set into downstream request header : {}", correlationId);
        wrapper.header("correlationId", correlationId);
        return exchangeFunction.exchange(wrapper.buildRequest());
    }

    private static final class ClientRequestWrapper implements HttpClientRequest {
        final ClientRequest delegate;
        final ClientRequest.Builder builder;

        ClientRequestWrapper(ClientRequest delegate) {
            this.delegate = delegate;
            this.builder = ClientRequest.from(delegate);
        }

        public Collection<String> headerNames() {
            return this.delegate.headers().keySet();
        }

        public Object unwrap() {
            return this.delegate;
        }

        public String method() {
            return this.delegate.method().name();
        }

        public String path() {
            return this.delegate.url().getPath();
        }

        public String url() {
            return this.delegate.url().toString();
        }

        public String header(String name) {
            return this.delegate.headers().getFirst(name);
        }

        public void header(String name, String value) {
            this.builder.header(name, value);
        }

        ClientRequest buildRequest() {
            return this.builder.build();
        }
    }

}
