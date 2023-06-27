package com.example.taxis.service;

import com.example.taxis.entity.SagencyVehicle;
import com.example.taxis.entity.SearchParams;
import com.example.taxis.entity.TaxiResponse;
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
public class WhiteListServiceImpl implements WhiteListService {

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

        if (whiteListRepository.existsWhiteListVehicleByVehicleIdAndTaxiOwnerBody(vehicleId, whiteListVehicle.getTaxiOwnerBody())) {
            throw new AlreadyExistsException("Vehicle already exists in the list");
        }

        if (whiteListRepository.existsWhiteListVehicleByVehicleIdAndTaxiOwnerBodyNot(vehicleId, vehicleTaxiOwner)) {
            throw new AlreadyExistsException("Vehicle already exists in the list with different status");
        }

        return whiteListRepository.save(whiteListVehicle);
    }



    @Override
    public void deleteFromWhiteList(SearchParams searchParams) {
        SagencyVehicle sagencyVehicle = sagencyRepository.findByVehicleIdAndGovNumber(searchParams.getVehicleId(), searchParams.getGovNumber());

        WhiteListVehicle whiteListVehicle = whiteListRepository.findByVehicleIdAndGovNumber(searchParams.getVehicleId(), searchParams.getGovNumber());
        if (sagencyVehicle.getVehicleId().equals(whiteListVehicle.getVehicleId()) && sagencyVehicle.getGovNumber().equals(whiteListVehicle.getGovNumber())) {
            whiteListVehicle.setActive(false);
            whiteListRepository.save(whiteListVehicle);

        }
      }

    @Override
    public TaxiResponse getWhiteListVehicle(SearchParams searchParams) {

        SagencyVehicle sagencyVehicle = sagencyRepository.findByVehicleIdAndGovNumber(searchParams.getVehicleId(), searchParams.getGovNumber());

        WhiteListVehicle whiteListVehicle = whiteListRepository.findByVehicleIdAndGovNumber(searchParams.getVehicleId(), searchParams.getGovNumber());
        if ((sagencyVehicle.getVehicleId().equals(whiteListVehicle.getVehicleId()) && sagencyVehicle.getGovNumber().equals(whiteListVehicle.getGovNumber()))) {
            TaxiResponse taxiResponse = new TaxiResponse(whiteListVehicle);
            return taxiResponse;
        }
        throw new NotFoundException("Vehicle not found in exceptions list");

    }
    }


