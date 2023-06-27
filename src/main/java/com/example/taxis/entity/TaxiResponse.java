package com.example.taxis.entity;

import com.example.taxis.service.WhiteListServiceImpl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Setter
@Getter
public class TaxiResponse {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime regDate;
    private String vin;
    private String ownerPersonalNo;

    public TaxiResponse(WhiteListVehicle whiteListVehicle) {
        this.regDate = whiteListVehicle.getRegDate();
        this.vin = whiteListVehicle.getVin();
        this.ownerPersonalNo = whiteListVehicle.getOwnerPersonalNo();
    }
}
