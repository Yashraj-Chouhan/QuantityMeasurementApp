package com.placement.backendquantitymeasurement.controller;

import com.placement.backendquantitymeasurement.dto.QuantityInputDTO;
import com.placement.backendquantitymeasurement.dto.QuantityMeasurementDTO;
import com.placement.backendquantitymeasurement.service.IQuantityMeasurementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quantity")
public class QuantityMeasurementController {

    private final IQuantityMeasurementService quantityMeasurementService;

    public QuantityMeasurementController(IQuantityMeasurementService quantityMeasurementService) {
        this.quantityMeasurementService = quantityMeasurementService;
    }

    @PostMapping("/compare")
    public ResponseEntity<QuantityMeasurementDTO> compare(@RequestBody QuantityInputDTO quantityInputDTO) {
        QuantityMeasurementDTO response = quantityMeasurementService.compare(
                quantityInputDTO.getThisQuantityDTO(),
                quantityInputDTO.getThatQuantityDTO()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<QuantityMeasurementDTO> add(@RequestBody QuantityInputDTO quantityInputDTO) {
        QuantityMeasurementDTO response = quantityMeasurementService.add(
                quantityInputDTO.getThisQuantityDTO(),
                quantityInputDTO.getThatQuantityDTO()
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
