package com.placement.backendquantitymeasurement.service;

import com.placement.backendquantitymeasurement.dto.*;

public interface IQuantityMeasurementService {

    QuantityMeasurementDTO compare(QuantityDTO a, QuantityDTO b);

    QuantityMeasurementDTO add(QuantityDTO a, QuantityDTO b);
}