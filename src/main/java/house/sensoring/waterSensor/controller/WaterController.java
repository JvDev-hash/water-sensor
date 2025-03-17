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

@RestController
@RequestMapping("/water")
public class WaterController {

    private final WaterService waterService;

    public WaterController(WaterService waterService) {
        this.waterService = waterService;
    }

    @GetMapping("/readings")
    public ResponseEntity<Water> getAllWaterReadings() {
        Water reading = waterService.getAllWaterReadings();
        if (reading == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(reading);
    }

    @PostMapping("/reading")
    public ResponseEntity<Water> saveWaterReading(@RequestBody Water water) {
        waterService.saveWaterReading(water);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}