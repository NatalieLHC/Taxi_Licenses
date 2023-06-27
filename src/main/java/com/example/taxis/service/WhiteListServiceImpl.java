package com.example.taxis.service;

import com.example.taxis.entity.*;
import com.example.taxis.exceptions.AlreadyExistsException;
import com.example.taxis.exceptions.BadRequestException;
import com.example.taxis.exceptions.NotFoundException;
import com.example.taxis.repository.LogRepository;
import com.example.taxis.repository.SagencyRepository;
import com.example.taxis.repository.WhiteListRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;

@Service
public class WhiteListServiceImpl implements WhiteListService {

    private final SagencyRepository sagencyRepository;
    private final WhiteListRepository whiteListRepository;
    private final LogRepository logRepository;

    public WhiteListServiceImpl(SagencyRepository sagencyRepository, WhiteListRepository whiteListRepository,  LogRepository logRepository) {
        this.sagencyRepository = sagencyRepository;
        this.whiteListRepository = whiteListRepository;
        this.logRepository = logRepository;
    }

    public int getLogCountForDay(LocalDate date) {
        return logRepository.getLogCountForDay(date);
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
        LocalDate currentDate = LocalDate.now();
        int requestCount = getLogCountForDay(currentDate);
        if (requestCount>3){
            throw new BadRequestException("Add request limit exceeded");
        }

        if (whiteListRepository.existsWhiteListVehicleByVehicleIdAndTaxiOwnerBody(vehicleId, whiteListVehicle.getTaxiOwnerBody())) {
            throw new AlreadyExistsException("Vehicle already exists in the list");
        }

        if (whiteListRepository.existsWhiteListVehicleByVehicleIdAndTaxiOwnerBodyNot(vehicleId, vehicleTaxiOwner)) {
            throw new AlreadyExistsException("Vehicle already exists in the list with different status");
        }
        RequestLogs logs = new RequestLogs(whiteListVehicle);
        logs.setRequestType("POST");
        logs.setRegDate(LocalDateTime.now());
        logRepository.save(logs);
        return whiteListRepository.save(whiteListVehicle);

    }


    @Override
    public void deleteFromWhiteList(SearchParams searchParams) {
        LocalDate currentDate = LocalDate.now();
        int requestCount = getLogCountForDay(currentDate);
        if (requestCount>3){
            throw new BadRequestException("Add request limit exceeded");
        }
        SagencyVehicle sagencyVehicle = sagencyRepository.findByVehicleIdAndGovNumber(searchParams.getVehicleId(), searchParams.getGovNumber());

        WhiteListVehicle whiteListVehicle = whiteListRepository.findByVehicleIdAndGovNumber(searchParams.getVehicleId(), searchParams.getGovNumber());
        if (sagencyVehicle.getVehicleId().equals(whiteListVehicle.getVehicleId()) && sagencyVehicle.getGovNumber().equals(whiteListVehicle.getGovNumber())) {
            whiteListVehicle.setActive(false);

            RequestLogs logs = new RequestLogs(whiteListVehicle);
            logs.setRequestType("DELETE");
            logs.setRegDate(whiteListVehicle.getRegDate());
            logRepository.save(logs);
            whiteListRepository.save(whiteListVehicle);

        }
        throw new BadRequestException("bad request");
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


