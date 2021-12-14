package com.wefox.tech.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentValidationRequestDto {

    private String payment_id;

    private Long account_id;

    private String payment_type;

    private String credit_card;

    private Long amount;

}
