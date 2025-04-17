package house.sensoring.waterSensor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import house.sensoring.waterSensor.model.Water;
import house.sensoring.waterSensor.service.WaterService;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/water")
public class WaterController {

    private final WaterService waterService;

    public WaterController(WaterService waterService) {
        this.waterService = waterService;
    }

    @GetMapping("/readings")
    public ResponseEntity<?> getAllWaterReadings() {
        List<Water> reading = waterService.getAllWaterReadings();
        if (reading.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(reading);
    }

    @GetMapping("/reading")
    public ResponseEntity<?> getLastReading() {
        try {
            Water reading = waterService.getLastWaterReading();
            return new ResponseEntity<>(reading, HttpStatus.OK);
        } catch (Exception e) {
            if (e.getClass().getName().equals("jakarta.persistence.EntityNotFoundException")) {
                return new ResponseEntity<>(e.getMessage().getBytes(StandardCharsets.UTF_8), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>("Internal Server Error: " + e.getClass().getName(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PostMapping("/reading")
    public ResponseEntity<Water> saveWaterReading(@RequestBody Water water) {
        waterService.saveWaterReading(water);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}