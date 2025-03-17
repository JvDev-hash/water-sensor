package house.sensoring.waterSensor.service;

import java.util.Date;
import java.util.List;

import house.sensoring.waterSensor.model.Water;
import house.sensoring.waterSensor.repository.WaterRepository;
import org.springframework.stereotype.Service;

@Service
public class WaterService {
    private final WaterRepository waterRepository;

    public WaterService(WaterRepository waterRepository) {
        this.waterRepository = waterRepository;
    }

    public Water getAllWaterReadings() {
        List<Water> reading = waterRepository.findAll();
        System.out.println(reading);
        if (reading.isEmpty()) {
            return null;
        }
        return reading.get(0);
    }

    public Water saveWaterReading(Water water) {
        water.setTimestamp(new Date());
        return waterRepository.save(water);
    }
}
