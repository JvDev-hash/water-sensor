# Water Sensor Project

A Spring Boot application that monitors water sensor readings and provides REST API endpoints to manage the data.

## Features

- REST API endpoints for water sensor readings
- H2 in-memory database for data storage
- Docker support for containerization
- Spring Boot 3.x with Java 17

## Prerequisites

- Java 17 or later
- Maven 3.6 or later
- Docker (optional, for containerized deployment)

## Getting Started

### Local Development

1. Clone the repository:
```bash
git clone https://github.com/JvDev-hash/water-sensor.git
cd water-sensor
```

2. Build the project:
```bash
./mvnw clean install
```

3. Run the application:
```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

### Docker Deployment

1. Build the Docker image:
```bash
docker build -t water-sensor .
```

2. Run the container:
```bash
docker run -p 8080:8080 water-sensor
```

## API Endpoints

### GET /water/readings
Retrieves the latest water sensor reading.

**Response:**
```json
{
    "id": 1,
    "hasWater": true,
    "timestamp": "2024-03-21T10:30:00.000+00:00"
}
```

### POST /water/reading
Creates a new water sensor reading.

**Request Body:**
```json
{
    "hasWater": true
}
```

## H2 Console

The H2 database console is available at `http://localhost:8080/h2-console` when running in development mode.

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 