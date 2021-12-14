package com.wefox.tech.control.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.wefox.tech.control.mapper.PaymentMapper;
import com.wefox.tech.entity.dto.PaymentDto;
import com.wefox.tech.entity.dto.PaymentValidationRequestDto;

import reactor.core.publisher.Mono;

@Service
public class PaymentValidationClient {

    private final WebClient paymentValidation;

    public PaymentValidationClient(@Value("${app.service-payment}") String paymentService) {
        this.paymentValidation = WebClient.builder()
            .baseUrl(paymentService)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    public boolean validatePayment(PaymentDto paymentDto) {

        PaymentValidationRequestDto paymentValidationRequestDto =
            PaymentMapper.getInstance().paymentDtoToValidationRequestDto(paymentDto);

        HttpStatus result = paymentValidation.post()
            .body(Mono.just(paymentValidationRequestDto), PaymentValidationRequestDto.class)
            .exchangeToMono(clientResponse -> Mono.just(clientResponse.statusCode()))
            .block();

        if (result != null && result.is2xxSuccessful()) {
            return true;
        } else {
            return false;
        }

    }
}
