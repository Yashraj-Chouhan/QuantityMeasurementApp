package com.placement.backendquantitymeasurement.dto;

import lombok.Data;

@Data
public class QuantityInputDTO {
    private QuantityDTO thisQuantityDTO;
    private QuantityDTO thatQuantityDTO;
}