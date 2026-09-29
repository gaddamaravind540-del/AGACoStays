# AGA CoStays - Room Service

Base package: `com.agacostays.room`
Database: `room_db`
Port: `8085`

## Main APIs

- `POST /api/manager/hotel-branches/{branchId}/rooms`
- `GET /api/hotel-branches/{branchId}/rooms`
- `GET /api/hotel-branches/{branchId}/rooms/{roomId}`
- `PUT /api/manager/hotel-branches/{branchId}/rooms/{roomId}`
- `DELETE /api/manager/hotel-branches/{branchId}/rooms/{roomId}`
- `PUT /api/manager/hotel-branches/{branchId}/rooms/{roomId}/status`
- `PUT /api/manager/hotel-branches/{branchId}/rooms/{roomId}/price`
- `GET /api/manager/hotel-branches/{branchId}/rooms/{roomId}/price-history`
- `GET /api/hotel-branches/{branchId}/rooms/availability`
- `POST /api/manager/hotel-branches/{branchId}/rooms/{roomId}/photos`
- `GET /api/hotel-branches/{branchId}/rooms/{roomId}/photos`
- `DELETE /api/manager/hotel-branches/{branchId}/rooms/{roomId}/photos/{photoId}`

## Local prerequisites

1. PostgreSQL database named `room_db`.
2. Kafka at `localhost:9092` when Kafka functionality is enabled.
3. Branch Service at `http://localhost:8083`.
4. Booking Service at `http://localhost:8086`.

## Build

```bash
mvn clean package
java -jar target/room-service-0.0.1-SNAPSHOT.jar
```

The Room Service source follows the package structure in the supplied AGA CoStays architecture document.

Configuration uses `application.properties`, `application-dev.properties`, and `application-prod.properties` (YAML files are not used).
