package com.example.taxis.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "white_list")
@SequenceGenerator(name = "WhiteListVehicleGenerator", sequenceName = "white_list_id_seq",allocationSize = 1)

public class WhiteListVehicle {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "WhiteListVehicleGenerator")
    private Integer id;
    @Column(name = "gov_number" , nullable = false)
    private String govNumber;
    private String vin;
    @Column(name = "engine_no" )
    private String engineNo;
    @Column(name = "chasses_no" )
    private String chassesNo;
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
    private boolean active;

    @PrePersist
    public void prePersist(){
        regDate = LocalDateTime.now();
        active = true;
    }
}
