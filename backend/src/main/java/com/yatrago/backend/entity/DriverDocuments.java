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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "driver_documents")
public class DriverDocuments {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CommonEnums.VehicleDocumentType documentType;

    @Column(nullable = false, unique = true)
    private String documentNo;

    @Column(nullable = false)
    private String fileURL;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CommonEnums.DocumentVerificationStatus verificationStatus;

    @CreationTimestamp
    private Timestamp uploadedAt;

    @Column(nullable = false)
    private Timestamp expiresAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "driver_id")
    DriversEntity driversEntity;
}
