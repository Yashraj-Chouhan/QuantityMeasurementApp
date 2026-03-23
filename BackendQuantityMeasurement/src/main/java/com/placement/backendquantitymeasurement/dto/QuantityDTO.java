package com.placement.backendquantitymeasurement.dto;

import lombok.Data;

@Data
public class QuantityDTO {
    private Double value;
    private String unit;
    private String measurementType;
}