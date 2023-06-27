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

    void deleteFromWhiteList(SearchParams searchParams);

    WhiteListVehicle getWhiteLiatVehicle(SearchParams searchParams);
}
