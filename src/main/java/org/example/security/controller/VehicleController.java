package org.example.security.controller;

/*
  @author   Tony
  @project   security
  @class  VehicleController
  @version  1.0.0 
  @since 30.09.2026 - 21.19
*/

import lombok.RequiredArgsConstructor;
import org.example.security.model.Vehicle;
import org.example.security.service.VehicleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPERADMIN')")
    public List<Vehicle> getAllVehicles() { return vehicleService.getAll(); }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPERADMIN')")
    public Vehicle getVehicleById(@PathVariable String id) { return vehicleService.getById(id); }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public Vehicle createVehicle(@RequestBody Vehicle vehicle) { return vehicleService.create(vehicle); }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public Vehicle updateVehicle(@PathVariable String id, @RequestBody Vehicle vehicle) { return vehicleService.update(id, vehicle); }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public void deleteVehicle(@PathVariable String id) { vehicleService.delete(id); }

    @PostMapping("/{id}/rent")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPERADMIN')")
    public Vehicle rentVehicle(@PathVariable String id, @RequestParam String userId) {
        return vehicleService.rentVehicle(id, userId);
    }
}
