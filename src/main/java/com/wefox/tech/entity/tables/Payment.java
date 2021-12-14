package com.wefox.tech.entity.tables;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "payments")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class Payment {

    @Id
    private String paymentId;

    private Long accountId;

    private String paymentType;

    private String creditCard;

    private Long amount;

    private Timestamp createdOn;
}
