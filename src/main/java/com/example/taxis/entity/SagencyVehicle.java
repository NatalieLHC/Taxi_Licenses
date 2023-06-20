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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "")

}
