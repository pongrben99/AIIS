package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.dto.AlertDto;
import com.aldisued.iot.monitoring.entity.Alert;
import com.aldisued.iot.monitoring.repository.AlertRepository;
import com.aldisued.iot.monitoring.repository.SensorRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class AlertService {

    private static final String KAFKA_TOPIC = "alerts";

    private final AlertRepository alertRepository;
    private final SensorRepository sensorRepository;
    private final KafkaTemplate<String, AlertDto> kafkaTemplate;

    public AlertService(AlertRepository alertRepository, SensorRepository sensorRepository,
                        KafkaTemplate<String, AlertDto> kafkaTemplate) {
        this.alertRepository = alertRepository;
        this.sensorRepository = sensorRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Should check all the edgecases
     */
    public Alert saveAlert(AlertDto alertDto) {
        // TODO: Task 6
        Objects.requireNonNull(alertDto, "AlertDto is null.");
        Alert alert = alertRepository.saveAndFlush(new Alert(alertDto.message(), alertDto.timestamp(), sensorRepository.getReferenceById(alertDto.sensorId())));
        kafkaTemplate.send(KAFKA_TOPIC, alertDto);

        return alert;
    }

    /**
     * Here i would throw back an exception what a exception handler could handle in a restcontrolleradvice but rn not overthinking anything.
     */
    public Optional<AlertDto> findLastAlertBySensorId(UUID sensorId) {
        // TODO: Task 5
        return alertRepository.findTopBySensorIdOrderByTimestampDesc(sensorId).map(p1 -> new AlertDto(p1.getSensor().getId(), p1.getMessage(), p1.getTimestamp()));
    }
}
