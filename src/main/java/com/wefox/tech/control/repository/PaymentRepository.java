package com.wefox.tech.control.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wefox.tech.entity.tables.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}
