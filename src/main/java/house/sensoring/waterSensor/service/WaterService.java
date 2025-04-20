package house.sensoring.waterSensor.service;

import java.util.Date;
import java.util.DuplicateFormatFlagsException;
import java.util.List;

import house.sensoring.waterSensor.DTO.WaterDTO;
import house.sensoring.waterSensor.model.Water;
import house.sensoring.waterSensor.repository.WaterRepository;
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
        Water reading = waterRepository.findTop1ByOrderByTimestampDesc()
                .orElseThrow(() -> new NoSuchFieldError("There is no entity: "));
        System.out.println(reading);
        return reading;
    }

    public Water saveWaterReading(WaterDTO water) {
        Water lastReading = waterRepository.findTop1ByOrderByTimestampDesc()
                .orElseThrow(() -> new NoSuchFieldError("There is no entity: "));

        if(!lastReading.getHasWater().equals(water.getHasWater())) {
            var waterReading = new Water();
            waterReading.setHasWater(water.getHasWater());
            waterReading.setTimestamp(new Date());
            return waterRepository.save(waterReading);
        } else {
            throw new DuplicateFormatFlagsException("Duplicate value");
        }
    }

    public Water startWaterReading(WaterDTO water) {
        var waterReading = new Water();
        waterReading.setHasWater(water.getHasWater());
        waterReading.setTimestamp(new Date());
        return waterRepository.save(waterReading);
    }
}
