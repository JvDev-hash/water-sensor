package house.sensoring.waterSensor;

import house.sensoring.waterSensor.DTO.WaterDTO;
import house.sensoring.waterSensor.controller.WaterController;
import house.sensoring.waterSensor.model.Water;
import house.sensoring.waterSensor.service.WaterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WaterControllerTest {

    @Mock
    private WaterService waterService;

    @InjectMocks
    private WaterController waterController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllWaterReadings_WhenReadingsExist_ReturnsOkWithReadings() {
        // Arrange
        List<Water> expectedReadings = Arrays.asList(
                new Water("1", true, new Date()),
                new Water("2", false, new Date())
        );
        when(waterService.getAllWaterReadings()).thenReturn(expectedReadings);

        // Act
        ResponseEntity<?> response = waterController.getAllWaterReadings();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedReadings, response.getBody());
    }

    @Test
    void getAllWaterReadings_WhenNoReadingsExist_ReturnsNotFound() {
        // Arrange
        when(waterService.getAllWaterReadings()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<?> response = waterController.getAllWaterReadings();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getLastReading_WhenReadingExists_ReturnsOkWithReading() {
        // Arrange
        Water expectedReading = new Water("1", true, new Date());
        when(waterService.getLastWaterReading()).thenReturn(expectedReading);

        // Act
        ResponseEntity<?> response = waterController.getLastReading();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedReading, response.getBody());
    }

    @Test
    void getLastReading_WhenNoSuchFieldError_ReturnsNotFound() {
        // Arrange
        NoSuchFieldError error = new NoSuchFieldError("No readings available");
        when(waterService.getLastWaterReading()).thenThrow(error);

        // Act
        ResponseEntity<?> response = waterController.getLastReading();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertArrayEquals(error.getMessage().getBytes(), (byte[]) response.getBody());
    }

    @Test
    void getLastReading_WhenOtherException_ReturnsInternalServerError() {
        // Arrange
        RuntimeException error = new RuntimeException("Unexpected error");
        when(waterService.getLastWaterReading()).thenThrow(error);

        // Act
        ResponseEntity<?> response = waterController.getLastReading();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Internal Server Error"));
    }

    @Test
    void saveWaterReading_WhenSuccessful_ReturnsCreated() {
        // Arrange
        WaterDTO waterDTO = new WaterDTO(true);

        // Act
        ResponseEntity<?> response = waterController.saveWaterReading(waterDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(waterService).saveWaterReading(waterDTO);
    }

    @Test
    void saveWaterReading_WhenNoSuchFieldError_ReturnsNotFound() {
        // Arrange
        WaterDTO waterDTO = new WaterDTO(true);
        NoSuchFieldError error = new NoSuchFieldError("Invalid field");
        doThrow(error).when(waterService).saveWaterReading(waterDTO);

        // Act
        ResponseEntity<?> response = waterController.saveWaterReading(waterDTO);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertArrayEquals(error.getMessage().getBytes(), (byte[]) response.getBody());
    }

    @Test
    void saveWaterReading_WhenDuplicateFormatFlagsException_ReturnsNoContent() {
        // Arrange
        WaterDTO waterDTO = new WaterDTO(true);
        DuplicateFormatFlagsException error = new DuplicateFormatFlagsException("Duplicate");
        doThrow(error).when(waterService).saveWaterReading(waterDTO);

        // Act
        ResponseEntity<?> response = waterController.saveWaterReading(waterDTO);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).toString().contains("Internal Server Error"));
    }

    @Test
    void startWaterSensor_WhenSuccessful_ReturnsCreated() {
        // Arrange
        WaterDTO waterDTO = new WaterDTO(true);

        // Act
        ResponseEntity<?> response = waterController.startWaterSensor(waterDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(waterService).startWaterReading(waterDTO);
    }

    @Test
    void startWaterSensor_WhenException_ReturnsInternalServerError() {
        // Arrange
        WaterDTO waterDTO = new WaterDTO(true);
        RuntimeException error = new RuntimeException("Start failed");
        doThrow(error).when(waterService).startWaterReading(waterDTO);

        // Act
        ResponseEntity<?> response = waterController.startWaterSensor(waterDTO);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).toString().contains("Internal Server Error"));
    }
}