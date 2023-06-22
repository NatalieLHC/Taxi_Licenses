package com.example.taxis.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "sagency")
@SequenceGenerator(name = "SagencyVehicleGenerator", sequenceName = "sagency_id_seq",allocationSize = 1)

public class SagencyVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SagencyVehicleGenerator")
    private Integer id;
    @Column(name = "vehicle_id" )
    private Integer vehicleId;
    @Column(name = "gov_number" , nullable = false)
    private String govNumber;
    @Column(name = "cert_number" )
    private String certNumber;
    private String vin;
    @Column(name = "engine_no" )
    private String engineNo;
    private String color;
    @Column(name = "vehicle_age" )
    private Integer vehicleAge;
    @Column(name = "chasis_no" )
    private String chassesNo;
    @Column(name = "personal_no" )
    private String personalNo;
    @Column(name = "first_name" )
    private String firstName;
    @Column(name = "last_name" )
    private String lastName;
    @Column(name = "reg_active" )
    private Integer regActive;
}
