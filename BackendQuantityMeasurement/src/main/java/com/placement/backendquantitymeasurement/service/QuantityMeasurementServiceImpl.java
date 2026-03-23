package com.placement.backendquantitymeasurement.service;

import com.placement.backendquantitymeasurement.dto.*;
import com.placement.backendquantitymeasurement.model.QuantityMeasurementEntity;
import com.placement.backendquantitymeasurement.repository.QuantityMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    @Autowired
    private QuantityMeasurementRepository repository;

    @Override
    public QuantityMeasurementDTO compare(QuantityDTO a, QuantityDTO b) {

        boolean result = a.getValue().equals(b.getValue());

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.setThisValue(a.getValue());
        entity.setThatValue(b.getValue());
        entity.setOperation("COMPARE");
        entity.setResultString(String.valueOf(result));

        repository.save(entity);

        return new QuantityMeasurementDTO(
                a.getValue(), a.getUnit(), a.getMeasurementType(),
                b.getValue(), b.getUnit(), b.getMeasurementType(),
                "COMPARE",
                null, null, null,
                String.valueOf(result),
                null, false
        );
    }

    @Override
    public QuantityMeasurementDTO add(QuantityDTO a, QuantityDTO b) {

        double result = a.getValue() + b.getValue();

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.setOperation("ADD");
        entity.setResultValue(result);

        repository.save(entity);

        return new QuantityMeasurementDTO(
                a.getValue(), a.getUnit(), a.getMeasurementType(),
                b.getValue(), b.getUnit(), b.getMeasurementType(),
                "ADD",
                result, a.getUnit(), a.getMeasurementType(),
                null,
                null, false
        );
    }
}