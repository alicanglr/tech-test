package com.wefox.tech.control.service;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wefox.tech.control.client.PaymentErrorLogClient;
import com.wefox.tech.control.repository.AccountRepository;
import com.wefox.tech.control.repository.PaymentRepository;
import com.wefox.tech.entity.dto.PaymentDto;
import com.wefox.tech.entity.tables.Account;

@ExtendWith(MockitoExtension.class)
class OfflineServiceTest {

    @Mock
    PaymentErrorLogClient paymentErrorLogClient;

    @Mock
    AccountRepository accountRepository;

    @Mock
    PaymentRepository paymentRepository;

    @InjectMocks
    OfflineService offlineService;

    @Test
    void offlineService_shouldSavePayment_whenParamsValid() {

        Mockito.when(accountRepository.findByAccountId(Mockito.any())).thenReturn(Optional.of(new Account()));

        offlineService.consumeOfflinePayment(preparePaymentDto());

        Mockito.verify(paymentRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(accountRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void offlineService_shouldSendErrorLog_whenMathcingAccountCouldNotFound() {

        Mockito.when(accountRepository.findByAccountId(Mockito.any())).thenReturn(Optional.empty());

        offlineService.consumeOfflinePayment(preparePaymentDto());

        Mockito.verify(paymentErrorLogClient, Mockito.times(1)).logError(Mockito.any());
    }

    @Test
    void offlineService_shouldSendErrorLog_whenThereIsExceptionOccur() {

        Mockito.when(accountRepository.findByAccountId(Mockito.any())).thenThrow(new NullPointerException());

        offlineService.consumeOfflinePayment(preparePaymentDto());

        Mockito.verify(paymentErrorLogClient, Mockito.times(1)).logError(Mockito.any());
    }

    private PaymentDto preparePaymentDto() {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setPayment_id("payment-id-example");
        paymentDto.setPayment_type("offline");
        paymentDto.setAccount_id(123L);
        paymentDto.setAmount(1L);
        paymentDto.setDelay(12L);
        paymentDto.setCredit_card("");
        return paymentDto;
    }
}