package com.example.taxis.service;

import com.example.taxis.entity.SagencyVehicle;
import com.example.taxis.entity.SearchParams;
import com.example.taxis.entity.WhiteListVehicle;
import com.example.taxis.exceptions.AlreadyExistsException;
import com.example.taxis.exceptions.BadRequestException;
import com.example.taxis.exceptions.NotFoundException;
import com.example.taxis.repository.SagencyRepository;
import com.example.taxis.repository.WhiteListRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

@Service
public class WhiteListServiceImpl implements WhiteListService{

    private final SagencyRepository sagencyRepository;
    private final WhiteListRepository whiteListRepository;

    public WhiteListServiceImpl(SagencyRepository sagencyRepository, WhiteListRepository whiteListRepository) {
        this.sagencyRepository = sagencyRepository;
        this.whiteListRepository = whiteListRepository;
    }


    @Override
    public WhiteListVehicle searchAndConvertVehicle(int vehicleId, String govNumber) {
        SagencyVehicle sagencyVehicle = sagencyRepository.findByVehicleIdAndGovNumberAndRegActive(vehicleId, govNumber, 1);

        if (sagencyVehicle == null) {
            throw new NotFoundException("400 - vehicle not found");
        }
        if (!sagencyVehicle.getColor().equals("white")) {
            throw new BadRequestException("400 - vehicle license parameters don't match");
        }
        int currentAge = Year.now().getValue();
        int carAge = sagencyVehicle.getVehicleAge();

        if (currentAge - carAge > 10) {
            throw new BadRequestException("400 - vehicle license parameters don't match");
        }
            WhiteListVehicle whiteListVehicle = new WhiteListVehicle(sagencyVehicle);

            return whiteListVehicle;
        }



    @Override
    public WhiteListVehicle addToWhiteList(SearchParams searchParams) {
        WhiteListVehicle whiteListVehicle = searchAndConvertVehicle(searchParams.getVehicleId(), searchParams.getGovNumber());
        int vehicleId = whiteListVehicle.getVehicleId();
        String vehicleTaxiOwner = "ლიცენზირებული ტაქსები";

        if (whiteListRepository.existsWhiteListVehicleByVehicleIdAndTaxiOwnerBodyNot(vehicleId, vehicleTaxiOwner) ){
            throw new AlreadyExistsException("Vehicle already exists in the list");
        }
        return whiteListRepository.save(whiteListVehicle);
    }

    @Override
    public WhiteListVehicle deleteFromWhiteList(WhiteListVehicle whiteListVehicle) {
        return null;
    }

    @Override
    public List<WhiteListVehicle> getWhiteListVehicles() {
        return null;
    }

//    @Override
//    public List<WhiteListVehicle> getWhiteListVehicles() {
//        return null;
//    }
}