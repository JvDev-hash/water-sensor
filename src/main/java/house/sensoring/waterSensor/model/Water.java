package house.sensoring.waterSensor.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("water")
public class Water {

    @Id
    private String id;

    private Boolean hasWater;

    private Date timestamp;
}
