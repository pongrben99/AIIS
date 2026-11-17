package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.entity.SensorType;
import com.aldisued.iot.monitoring.repository.SensorReadingRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MeasurementService {

  private final SensorReadingRepository sensorReadingRepository;

  public MeasurementService(SensorReadingRepository sensorReadingRepository) {
    this.sensorReadingRepository = sensorReadingRepository;
  }

  public List<Double> getMeasurementValuesBySensorType(SensorType sensorType, LocalDateTime from,
      LocalDateTime to) {
    // TODO: Task 8
    return List.of();
  }

    /**
     * always use the db for these calculations if we solve with streams we could get OOM and slower than let the DB handle this calculation.
     */
  public Optional<Double> getAverageTemperature(LocalDateTime from, LocalDateTime to) {
    // TODO: Task 7
    return sensorReadingRepository.findAverageValueBySensorTypeAndTimestampBetween(
            SensorType.TEMPERATURE, from, to);
  }

}
