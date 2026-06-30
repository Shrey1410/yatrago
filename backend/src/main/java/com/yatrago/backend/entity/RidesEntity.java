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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "rides")
public class RidesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CommonEnums.RideStatus rideStatus;

    @Column(nullable = false)
    private Double sourceLat;

    @Column(nullable = false)
    private Double sourceLng;

    @Column(nullable = false)
    private Double destinationLat;

    @Column(nullable = false)
    private Double destinationLng;

    @CreationTimestamp
    private Timestamp createdAt;

    @Column(nullable = false)
    private String pickUpAddress;

    @Column(nullable = false)
    private String dropOffAddress;

    @Column(nullable = false)
    private Double estimatedFare;

    private Double actualFare;

    private Timestamp startedAt;

    private Timestamp completedAt;

    private String cancelReason;

    @ManyToOne
    @JoinColumn(name = "rider_user_id", nullable = false)
    private UsersEntity usersEntity;

    @ManyToOne
    @JoinColumn(name = "driver_user_id")
    private DriversEntity driversEntity;

    @OneToOne(mappedBy = "ridesEntity")
    private RideFeedbacksEntity rideFeedbacksEntity;

    @OneToOne(mappedBy = "ridesEntity")
    private PaymentsEntity paymentsEntity;
}
