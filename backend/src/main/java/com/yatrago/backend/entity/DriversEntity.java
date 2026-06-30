package com.yatrago.backend.entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.yatrago.backend.common.CommonEnums;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "driver_users")
@Getter
@Setter
public class DriversEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "Please provide valid email")
    private String email;

    @Column(nullable = false)
    private String firstName;

    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String countryCode;

    @Column(nullable = false, unique = true)
    private String mobileNumber;

    @CreationTimestamp
    private Timestamp createdAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CommonEnums.Gender gender;

    @OneToOne(mappedBy = "driversEntity")
    private VehiclesEntity vehiclesEntity;

    @OneToMany(mappedBy = "driversEntity")
    private List<RidesEntity> ridesEntity;

    @OneToMany(mappedBy = "driverEntity")
    private List<DriverDocuments> driverDocuments;
}
