package com.wefox.tech.entity.tables;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Account {
    @Id
    private Long accountId;

    private String name;

    private String email;

    private Date birthdate;

    private Timestamp lastPaymentDate;

    private Timestamp createdOn;

}
