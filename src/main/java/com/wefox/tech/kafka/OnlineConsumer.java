package com.wefox.tech.kafka;

import java.io.IOException;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wefox.tech.control.service.OnlineService;
import com.wefox.tech.entity.dto.PaymentDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OnlineConsumer {

    private final OnlineService onlineService;

    @KafkaListener(topics = "online", groupId = "group_id")
    public void consume(String message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        PaymentDto paymentDto = objectMapper.readValue(message, PaymentDto.class);
        onlineService.consumeOnlinePayment(paymentDto);
    }
}
