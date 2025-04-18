package house.sensoring.waterSensor.service;

import java.util.Date;
import java.util.DuplicateFormatFlagsException;
import java.util.List;

import house.sensoring.waterSensor.model.Water;
import house.sensoring.waterSensor.repository.WaterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class WaterService {
    private final WaterRepository waterRepository;

    public WaterService(WaterRepository waterRepository) {
        this.waterRepository = waterRepository;
    }

    public List<Water> getAllWaterReadings() {
        List<Water> reading = waterRepository.findAll();
        System.out.println(reading);
        if (reading.isEmpty()) {
            return null;
        }
        return reading;
    }

    public Water getLastWaterReading() {
        Water reading = waterRepository.findLastReading()
                .orElseThrow(() -> new EntityNotFoundException("There is no entity: "));
        System.out.println(reading);
        return reading;
    }

    public Water saveWaterReading(Water water) {
        Water lastReading = waterRepository.findLastReading()
                .orElseThrow(() -> new EntityNotFoundException("There is no entity: "));

        if(!lastReading.getHasWater().equals(water.getHasWater())) {
            water.setTimestamp(new Date());
            return waterRepository.save(water);
        } else {
            throw new DuplicateFormatFlagsException("Duplicate value");
        }
    }
}
