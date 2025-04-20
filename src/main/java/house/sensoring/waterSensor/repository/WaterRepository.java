package house.sensoring.waterSensor.repository;

import house.sensoring.waterSensor.model.Water;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface WaterRepository extends MongoRepository<Water, UUID> {

    Optional<Water> findTop1ByOrderByTimestampDesc();
    
}
