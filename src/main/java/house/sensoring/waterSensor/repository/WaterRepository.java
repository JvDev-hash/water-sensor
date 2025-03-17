package house.sensoring.waterSensor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import house.sensoring.waterSensor.model.Water;

public interface WaterRepository extends JpaRepository<Water, Long> {
    
}
