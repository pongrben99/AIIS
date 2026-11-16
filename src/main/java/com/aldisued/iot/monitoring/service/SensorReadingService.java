package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.dto.SensorReadingDto;
import com.aldisued.iot.monitoring.entity.SensorReading;
import com.aldisued.iot.monitoring.repository.SensorReadingRepository;
import com.aldisued.iot.monitoring.repository.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SensorReadingService {

  private final SensorReadingRepository sensorReadingRepository;
  private final SensorRepository sensorRepository;

  public SensorReadingService(SensorReadingRepository sensorReadingRepository,
      SensorRepository sensorRepository) {
    this.sensorReadingRepository = sensorReadingRepository;
    this.sensorRepository = sensorRepository;
  }

    /**
     * since its rn just a homework and no real production environment we can ignore these null checks cuz only do "happy path",
     * but we should handle all the edge cases. (nullchecks, incoming data validation etc..)
     */
    //
  public SensorReading saveSensorReading(final SensorReadingDto sensorReadingDto) {
      Objects.requireNonNull(sensorReadingDto, "Sensor reading dto is null");

      //TODO: Task 3
      return sensorReadingRepository.saveAndFlush(new SensorReading(sensorReadingDto.value(), sensorReadingDto.timestamp(), sensorRepository.getReferenceById(sensorReadingDto.sensorId())));
  }

}
