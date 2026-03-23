package com.placement.backendquantitymeasurement.repository;

import com.placement.backendquantitymeasurement.model.QuantityMeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuantityMeasurementRepository
        extends JpaRepository<QuantityMeasurementEntity, Long> {

    List<QuantityMeasurementEntity> findByOperation(String operation);

    List<QuantityMeasurementEntity> findByThisMeasurementType(String type);

    List<QuantityMeasurementEntity> findByErrorTrue();

    long countByOperationAndErrorFalse(String operation);
}
