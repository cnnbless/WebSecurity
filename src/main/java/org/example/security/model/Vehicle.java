package org.example.security.model;

/*
  @author   Tony
  @project   security
  @class  Vehicle
  @version  1.0.0 
  @since 30.09.2026 - 21.20
*/
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {
    private String id;
    private String brand;
    private String model;
    private String registrationNumber;
    private String fleetId;
    private String rentedByUserId;
}