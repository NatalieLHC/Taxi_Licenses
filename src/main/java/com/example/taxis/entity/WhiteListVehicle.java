package com.example.taxis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "white_list")
@SequenceGenerator(name = "WhiteListVehicleGenerator", sequenceName = "white_list_id_seq",allocationSize = 1)

public class WhiteListVehicle {

    public WhiteListVehicle (SagencyVehicle sagencyVehicle){
        this.id = sagencyVehicle.getId();
        this.govNumber = sagencyVehicle.getGovNumber();
        this.vehicleId = sagencyVehicle.getVehicleId();
        this.vin = sagencyVehicle.getVin();
        this.engineNo = sagencyVehicle.getEngineNo();
        this.chassesNo = sagencyVehicle.getChassesNo();
        this.ownerPersonalNo = sagencyVehicle.getPersonalNo();
        this.ownerFirstName = sagencyVehicle.getFirstName();
        this.ownerLastName = sagencyVehicle.getLastName();
        this.vehicleAge = sagencyVehicle.getVehicleAge();
        this.color = sagencyVehicle.getColor();
        this.fineArticle = 125-0-2;
        this.addGround = "სერვისით ავტომატურად დამატებული";
        this.initiator = "სხვა უწყების მომართვა";
        this.taxiOwnerBody = "ლიცენზირებული ტაქსები";

    }

    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "WhiteListVehicleGenerator")
    private Integer id;
    @Column(name = "gov_number" , nullable = false)
    private String govNumber;
    @Column(name = "vehicle_id" )
    private Integer vehicleId;
    private String vin;
    @Column(name = "engine_no" )
    private String engineNo;
    @Column(name = "chasses_no" )
    private String chassesNo;
    private String color;
    @Column(name = "vehicle_age" )
    private Integer vehicleAge;
    @Column(name = "owner_personalno" )
    private String ownerPersonalNo;
    @Column(name = "owner_name" )
    private String ownerFirstName;
    @Column(name = "owner_lastname" )
    private String ownerLastName;
    @Column(name = "texi_owner_body" )
    private String taxiOwnerBody;
    @Column(name = "fine_article" )
    private Integer fineArticle;
    @Column(name = "add_ground" )
    private String addGround;
    private String initiator;
    @Column(name = "reg_time" )
    private LocalDateTime regDate;
    @Column(name = "active" )
    private boolean active;

    @PrePersist
    public void prePersist(){
        regDate = LocalDateTime.now();
        active = true;

    }
}
