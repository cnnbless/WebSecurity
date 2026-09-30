package org.example.security.controller;

/*
  @author   Tony
  @project   security
  @class  VehicleController
  @version  1.0.0 
  @since 30.09.2026 - 21.19
*/

import org.example.security.model.Vehicle;
import org.example.security.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;

    @GetMapping
    public List<Vehicle> getAllVehicles() { return vehicleService.getAll(); }

    @GetMapping("/{id}")
    public Vehicle getVehicleById(@PathVariable String id) { return vehicleService.getById(id); }

    @PostMapping
    public Vehicle createVehicle(@RequestBody Vehicle vehicle) { return vehicleService.create(vehicle); }

    @PutMapping("/{id}")
    public Vehicle updateVehicle(@PathVariable String id, @RequestBody Vehicle vehicle) { return vehicleService.update(id, vehicle); }

    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable String id) { vehicleService.delete(id); }


    @PostMapping("/{id}/rent")
    public Vehicle rentVehicle(@PathVariable String id, @RequestParam String userId) {
        return vehicleService.rentVehicle(id, userId);
    }
}