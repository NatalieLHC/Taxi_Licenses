package com.example.taxis.controller;

import com.example.taxis.entity.SearchParams;
import com.example.taxis.entity.WhiteListVehicle;
import com.example.taxis.service.WhiteListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/whitelist")
public class WhiteListController {

    private final WhiteListService whiteListService;

    public WhiteListController(WhiteListService whiteListService) {
        this.whiteListService = whiteListService;
    }

    @PostMapping("/addtowhitelist")
    public ResponseEntity<SearchParams> addToWhiteList(@RequestBody SearchParams searchParams){
      whiteListService.addToWhiteList(searchParams);
      var location = UriComponentsBuilder.fromPath("/whitelist/" + searchParams.getVehicleId()).build().toUri();
      return ResponseEntity.created(location).body(searchParams);
    }
}
