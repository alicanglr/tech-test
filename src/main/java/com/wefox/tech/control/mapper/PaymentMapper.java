package com.wefox.tech.control.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.wefox.tech.entity.dto.PaymentDto;
import com.wefox.tech.entity.dto.PaymentValidationRequestDto;
import com.wefox.tech.entity.tables.Payment;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper {

    static PaymentMapper getInstance() {
        return Mappers.getMapper(PaymentMapper.class);
    }

    @Mapping(source = "payment_id", target = "paymentId")
    @Mapping(source = "account_id", target = "accountId")
    @Mapping(source = "payment_type", target = "paymentType")
    @Mapping(source = "credit_card", target = "creditCard")
    Payment paymentDtoToPayment(PaymentDto paymentDto);

    PaymentValidationRequestDto paymentDtoToValidationRequestDto(PaymentDto paymentDto);
}
