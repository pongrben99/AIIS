package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.entity.SensorType;
import com.aldisued.iot.monitoring.repository.SensorReadingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MeasurementService {

    private final SensorReadingRepository sensorReadingRepository;

    public MeasurementService(SensorReadingRepository sensorReadingRepository) {
        this.sensorReadingRepository = sensorReadingRepository;
    }

    /**
     * Always use the db for these type of tasks.
     */
    public List<Double> getMeasurementValuesBySensorType(SensorType sensorType, LocalDateTime from,
                                                         LocalDateTime to) {
        Objects.requireNonNull(sensorType, "SensorType is null.");
        Objects.requireNonNull(from, "From is null.");
        Objects.requireNonNull(to, "To is null.");
        // TODO: Task 8
        return sensorReadingRepository.findValuesBySensorTypeAndTimestampBetweenOrderByTimestampAsc(sensorType, from, to);
    }

    /**
     * always use the db for these calculations if we solve with streams we could get OOM and slower than let the DB handle this calculation.
     */
    public Optional<Double> getAverageTemperature(LocalDateTime from, LocalDateTime to) {
        Objects.requireNonNull(from, "From is null.");
        Objects.requireNonNull(to, "To is null.");
        // TODO: Task 7
        return sensorReadingRepository.findAverageValueBySensorTypeAndTimestampBetween(
                SensorType.TEMPERATURE, from, to);
    }

}
