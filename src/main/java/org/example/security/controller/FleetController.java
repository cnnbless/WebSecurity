package org.example.security.controller;

/*
  @author   Tony
  @project   security
  @class  FleetController
  @version  1.0.0 
  @since 30.09.2026 - 21.19
*/

import lombok.RequiredArgsConstructor;
import org.example.security.model.Fleet;
import org.example.security.service.FleetService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/fleets")
@RequiredArgsConstructor
public class FleetController {
    private final FleetService fleetService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPERADMIN')")
    public List<Fleet> getAllFleets() { return fleetService.getAll(); }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'SUPERADMIN')")
    public Fleet getFleetById(@PathVariable String id) { return fleetService.getById(id); }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public Fleet createFleet(@RequestBody Fleet fleet) { return fleetService.create(fleet); }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public Fleet updateFleet(@PathVariable String id, @RequestBody Fleet fleet) { return fleetService.update(id, fleet); }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public void deleteFleet(@PathVariable String id) { fleetService.delete(id); }
}
