package org.example.security.service;

/*
  @author   Tony
  @project   security
  @class  VehicleService
  @version  1.0.0 
  @since 30.09.2026 - 21.21
*/

import org.example.security.model.Vehicle;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {
    private final List<Vehicle> vehicles = new ArrayList<>();

    public VehicleService() {
        vehicles.add(new Vehicle("1", "Toyota", "Camry", "CE1234AB", "1", null));
        vehicles.add(new Vehicle("2", "Volkswagen", "Golf", "CE5678CD", "1", "1")); // Орендовано користувачем "1"
    }

    public List<Vehicle> getAll() { return vehicles; }

    public Vehicle getById(String id) {
        return vehicles.stream().filter(v -> v.getId().equals(id)).findFirst().orElse(null);
    }

    public Vehicle create(Vehicle vehicle) {
        vehicles.add(vehicle);
        return vehicle;
    }

    public Vehicle update(String id, Vehicle updatedVehicle) {
        Vehicle oldVehicle = getById(id);
        if (oldVehicle != null) {
            vehicles.remove(oldVehicle);
            updatedVehicle.setId(id);
            vehicles.add(updatedVehicle);
            return updatedVehicle;
        }
        return null;
    }

    public void delete(String id) {
        Vehicle vehicle = getById(id);
        if (vehicle != null) vehicles.remove(vehicle);
    }

    public Vehicle rentVehicle(String vehicleId, String userId) {
        Vehicle vehicle = getById(vehicleId);
        if (vehicle != null && vehicle.getRentedByUserId() == null) {
            vehicle.setRentedByUserId(userId);
            return vehicle;
        }
        throw new IllegalStateException("Транспортний засіб недоступний або не існує");
    }
}