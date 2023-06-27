package com.example.taxis.controller;

import com.example.taxis.entity.SearchParams;
import com.example.taxis.entity.TaxiResponse;
import com.example.taxis.entity.WhiteListVehicle;
import com.example.taxis.repository.WhiteListRepository;
import com.example.taxis.service.WhiteListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/whitelist")
public class WhiteListController {

    private final WhiteListService whiteListService;
    private final WhiteListRepository whiteListRepository;

    public WhiteListController(WhiteListService whiteListService,
                               WhiteListRepository whiteListRepository) {
        this.whiteListService = whiteListService;
        this.whiteListRepository = whiteListRepository;
    }
    @GetMapping("/gettaxi")
    public TaxiResponse getTaxi(@RequestBody SearchParams searchParams){
        var taxiResponse = whiteListService.getWhiteListVehicle(searchParams);
        return taxiResponse;
    }

    @PostMapping("/addtowhitelist")
    public ResponseEntity<SearchParams> addToWhiteList(@RequestBody SearchParams searchParams){
      whiteListService.addToWhiteList(searchParams);
      var location = UriComponentsBuilder.fromPath("/whitelist/" + searchParams.getVehicleId()).build().toUri();
      return ResponseEntity.created(location).body(searchParams);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<SearchParams> deleteFromWhiteList (@RequestBody SearchParams searchParams){
        whiteListService.deleteFromWhiteList(searchParams);
        return ResponseEntity.noContent().build();
    }
}
