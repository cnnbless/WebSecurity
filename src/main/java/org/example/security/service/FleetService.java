package org.example.security.service;

/*
  @author   Tony
  @project   security
  @class  FleetService
  @version  1.0.0 
  @since 30.09.2026 - 21.21
*/

import org.example.security.model.Fleet;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class FleetService {
    private final List<Fleet> fleets = new ArrayList<>();

    public FleetService() {
        fleets.add(new Fleet("1", "Central Station Fleet", "Main St. 1", 50));
    }

    public List<Fleet> getAll() { return fleets; }

    public Fleet getById(String id) {
        return fleets.stream().filter(f -> f.getId().equals(id)).findFirst().orElse(null);
    }

    public Fleet create(Fleet fleet) {
        fleets.add(fleet);
        return fleet;
    }

    public Fleet update(String id, Fleet updatedFleet) {
        Fleet oldFleet = getById(id);
        if (oldFleet != null) {
            fleets.remove(oldFleet);
            updatedFleet.setId(id);
            fleets.add(updatedFleet);
            return updatedFleet;
        }
        return null;
    }

    public void delete(String id) {
        Fleet fleet = getById(id);
        if (fleet != null) fleets.remove(fleet);
    }
}