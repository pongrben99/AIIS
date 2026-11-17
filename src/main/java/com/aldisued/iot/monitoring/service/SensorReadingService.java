package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.dto.SensorReadingDto;
import com.aldisued.iot.monitoring.entity.Alert;
import com.aldisued.iot.monitoring.entity.Sensor;
import com.aldisued.iot.monitoring.entity.SensorReading;
import com.aldisued.iot.monitoring.entity.SensorType;
import com.aldisued.iot.monitoring.repository.AlertRepository;
import com.aldisued.iot.monitoring.repository.SensorReadingRepository;
import com.aldisued.iot.monitoring.repository.SensorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class SensorReadingService {

    private final SensorReadingRepository sensorReadingRepository;
    private final SensorRepository sensorRepository;
    private final AlertRepository alertRepository;

    public SensorReadingService(SensorReadingRepository sensorReadingRepository,
                                SensorRepository sensorRepository,
                                AlertRepository alertRepository) {
        this.sensorReadingRepository = sensorReadingRepository;
        this.sensorRepository = sensorRepository;
        this.alertRepository = alertRepository;
    }

    /**
     * Handle edgecases, nulls, etc...
     */
    @Transactional
    public SensorReading saveSensorReading(final SensorReadingDto sensorReadingDto) {
        //TODO: Task 3
        //TODO: Task 10
        Objects.requireNonNull(sensorReadingDto, "Sensor reading dto is null");

        Sensor sensor = sensorRepository.getReferenceById(sensorReadingDto.sensorId());

        Double value = sensorReadingDto.value();

        ruleChecker(sensor.getType(), value).ifPresent(message ->
                alertRepository.save(new Alert(message, sensorReadingDto.timestamp(), sensor))
        );

        return sensorReadingRepository.saveAndFlush(
                new SensorReading(value, sensorReadingDto.timestamp(), sensor)
        );
    }

    //maybe check for nulls
    private Optional<String> ruleChecker(final SensorType type, final Double value) {
        return switch (type) {
            case TEMPERATURE -> value > 40.0 || value < -20.0 ? Optional.of("Temperature out of range: " + value) : Optional.empty();
            case HUMIDITY -> value > 90.0 ? Optional.of("Humidity too high: " + value) : Optional.empty();
            case ATMOSPHERIC_PRESSURE -> value < 950.0 || value > 1050.0? Optional.of("Pressure out of range: " + value) : Optional.empty();
        };
    }


}
