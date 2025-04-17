package house.sensoring.waterSensor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import house.sensoring.waterSensor.model.Water;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WaterRepository extends JpaRepository<Water, Long> {

    @Query(value = "SELECT * FROM water w ORDER BY w.id DESC LIMIT 1", nativeQuery = true)
    Optional<Water> findLastReading();
    
}
