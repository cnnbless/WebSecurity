package org.example.security.model;

/*
  @author   Tony
  @project   security
  @class  User
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
public class User {
    private String id;
    private String email;
    private String fullName;
    private String driverLicense;
}
