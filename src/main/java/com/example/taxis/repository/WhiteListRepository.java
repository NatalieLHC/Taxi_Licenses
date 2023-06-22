package com.example.taxis.repository;

import com.example.taxis.entity.WhiteListVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WhiteListRepository extends JpaRepository<WhiteListVehicle, Integer> {
    boolean existsWhiteListVehicleByVehicleIdAndTaxiOwnerBodyNot(int vehicleId, String taxiOwnerBody);


}