package com.example.taxis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "request_logs")
@SequenceGenerator(name = "RequestLogSequenceGenerator", sequenceName = "request_logs_id_seq",allocationSize = 1)

public class RequestLogs {

    public RequestLogs(WhiteListVehicle whiteListVehicle){
        this.govNumber = whiteListVehicle.getGovNumber();
        this.vehicleId= whiteListVehicle.getVehicleId();
        this.RequestDate = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RequestLogSequenceGenerator")
    private Integer id;
    private String requestType;
    private Integer vehicleId;
    private String govNumber;
    private LocalDateTime RequestDate;
    private LocalDateTime regDate;


}
