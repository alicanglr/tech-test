package com.wefox.tech.control.service;

import org.springframework.stereotype.Service;

import com.wefox.tech.control.client.PaymentErrorLogClient;
import com.wefox.tech.control.repository.AccountRepository;
import com.wefox.tech.control.repository.PaymentRepository;
import com.wefox.tech.entity.ErrorType;
import com.wefox.tech.entity.dto.PaymentDto;
import com.wefox.tech.entity.dto.PaymentErrorLogDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OfflineService extends AbstractService {

    protected OfflineService(PaymentErrorLogClient logClient, AccountRepository accountRepository,
        PaymentRepository paymentRepository) {
        super(logClient, accountRepository, paymentRepository);
    }

    public void consumeOfflinePayment(PaymentDto paymentDto) {

        try {
            savePayment(paymentDto);
        } catch (Exception e) {

            logError(PaymentErrorLogDto.builder()
                .payment_id(paymentDto.getPayment_id())
                .error_type(ErrorType.INTERNAL.name())
                .error_description("Internal error happened while saving the payment")
                .build());
        }
    }
}
