package com.wefox.tech.control.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import com.wefox.tech.control.client.PaymentErrorLogClient;
import com.wefox.tech.control.mapper.PaymentMapper;
import com.wefox.tech.control.repository.AccountRepository;
import com.wefox.tech.control.repository.PaymentRepository;
import com.wefox.tech.entity.ErrorType;
import com.wefox.tech.entity.dto.PaymentDto;
import com.wefox.tech.entity.dto.PaymentErrorLogDto;
import com.wefox.tech.entity.tables.Account;
import com.wefox.tech.entity.tables.Payment;

public abstract class AbstractService {

    private PaymentErrorLogClient logClient;
    private AccountRepository accountRepository;
    private PaymentRepository paymentRepository;

    protected AbstractService(PaymentErrorLogClient logClient, AccountRepository accountRepository,
        PaymentRepository paymentRepository) {
        this.logClient = logClient;
        this.accountRepository = accountRepository;
        this.paymentRepository = paymentRepository;
    }

    protected void logError(PaymentErrorLogDto paymentErrorLogDto) {
        logClient.logError(paymentErrorLogDto);
    }

    protected void savePayment(PaymentDto paymentDto) {

        Payment payment = PaymentMapper.getInstance().paymentDtoToPayment(paymentDto);

        Optional<Account> optionalAccount = accountRepository.findByAccountId(payment.getAccountId());
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setLastPaymentDate(Timestamp.valueOf(LocalDateTime.now()));
            payment.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));

            paymentRepository.save(payment);
            accountRepository.save(account);
        } else {

            logError(PaymentErrorLogDto.builder()
                .payment_id(paymentDto.getPayment_id())
                .error_type(ErrorType.ACCOUNT_DOES_NOT_EXIST.name())
                .error_description("Account does not exist to match with payment.")
                .build());
        }
    }
}
