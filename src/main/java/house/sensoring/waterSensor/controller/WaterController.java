package house.sensoring.waterSensor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import house.sensoring.waterSensor.DTO.WaterDTO;
import house.sensoring.waterSensor.model.Water;
import house.sensoring.waterSensor.service.WaterService;

import java.nio.charset.StandardCharsets;
import java.util.DuplicateFormatFlagsException;
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
        } catch (NoSuchFieldError e) {
            return new ResponseEntity<>(e.getMessage().getBytes(StandardCharsets.UTF_8), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error: " + e.getClass().getName(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
    @PostMapping("/reading")
    public ResponseEntity<?> saveWaterReading(@RequestBody WaterDTO water) {
        try {
            waterService.saveWaterReading(water);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (NoSuchFieldError e){
            return new ResponseEntity<>(e.getMessage().getBytes(StandardCharsets.UTF_8), HttpStatus.NOT_FOUND);
        } catch (DuplicateFormatFlagsException e){
            return new ResponseEntity<>("Internal Server Error: " + e.getClass().getName(), HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/start")
    public ResponseEntity<?> startWaterSensor(@RequestBody WaterDTO water) {
        try {
            waterService.startWaterReading(water);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Internal Server Error: " + e.getClass().getName(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}