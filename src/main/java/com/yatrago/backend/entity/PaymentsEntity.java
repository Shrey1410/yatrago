package com.yatrago.backend.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.yatrago.backend.common.CommonEnums;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "payments")
public class PaymentsEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CommonEnums.PaymentMethod paymentMethod;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CommonEnums.PaymentStatus paymentStatus;

    @Column(nullable = false, unique = true)
    private String transaction_id;

    @CreationTimestamp
    private Timestamp paidAt;

    @OneToOne
    @JoinColumn(name = "ride_id", nullable = false)
    private RidesEntity ridesEntity;

}
