package com.aldisued.iot.monitoring.controller;

import com.aldisued.iot.monitoring.dto.AlertDto;
import com.aldisued.iot.monitoring.service.AlertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/alerts")
public class AlertController {

  private final AlertService alertService;

  public AlertController(AlertService alertService) {
    this.alertService = alertService;
  }

  @GetMapping(path = "/latest")
  public ResponseEntity<AlertDto> getEntityByUUID(@RequestParam(name="sensorId", required = true) final UUID sensorId) {
      Optional<AlertDto> optionalAlertDto = alertService.findLastAlertBySensorId(sensorId);
      return optionalAlertDto.map(alertDto -> new ResponseEntity<>(alertDto, HttpStatus.OK))
              .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

}
