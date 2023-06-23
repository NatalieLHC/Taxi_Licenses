package com.example.taxis.service;

import com.example.taxis.entity.SagencyVehicle;
import com.example.taxis.entity.SearchParams;
import com.example.taxis.entity.WhiteListVehicle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WhiteListService {

    WhiteListVehicle searchAndConvertVehicle(int vehicleId, String govNumber);

    WhiteListVehicle addToWhiteList (SearchParams searchParams);

    WhiteListVehicle deleteFromWhiteList(int vehicleId, String govNumber);

    List<WhiteListVehicle> getWhiteListVehicles();
}
