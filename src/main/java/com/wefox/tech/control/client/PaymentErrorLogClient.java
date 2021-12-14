package com.wefox.tech.control.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.wefox.tech.entity.dto.PaymentErrorLogDto;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PaymentErrorLogClient {

    private final WebClient errorLogClient;

    @Autowired
    public PaymentErrorLogClient(@Value("${app.service-log}") String logService) {
        this.errorLogClient = WebClient.builder()
            .baseUrl(logService)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    public void logError(PaymentErrorLogDto errorLogDto) {

        errorLogClient.post()
            .body(Mono.just(errorLogDto), PaymentErrorLogDto.class)
            .exchangeToMono(clientResponse -> Mono.just(clientResponse.statusCode()))
            .block();
    }
}
