package org.example.security;

/*
  @author   Tony
  @project   security
  @class  SecurityApplicationTests
  @version  1.0.0 
  @since 30.09.2026 - 21.15
*/

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class SecurityApplicationTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithAnonymousUser
    @DisplayName("1. Anonymous user -> GET /api/v1/vehicles -> 401 Unauthorized")
    void whenAnonymousAccessVehicles_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/vehicles"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    @DisplayName("2. Role USER -> GET /api/v1/vehicles -> 200 OK")
    void whenUserGetsAllVehicles_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/vehicles"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    @DisplayName("3. Role USER -> GET /api/v1/fleets -> 200 OK")
    void whenUserGetsAllFleets_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/fleets"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    @DisplayName("4. Role USER -> POST /api/v1/vehicles/1/rent -> 200 OK")
    void whenUserRentsVehicle_thenOk() throws Exception {
        mockMvc.perform(post("/api/v1/vehicles/1/rent")
                        .param("userId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    @DisplayName("5. Role USER -> GET /api/v1/users -> 403 Forbidden")
    void whenUserGetsAllUsers_thenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    @DisplayName("6. Role USER -> POST /api/v1/vehicles -> 403 Forbidden")
    void whenUserCreatesVehicle_thenForbidden() throws Exception {
        String vehicleJson = """
                {
                  "id": "10",
                  "brand": "Tesla",
                  "model": "Model 3",
                  "registrationNumber": "CE0001TT",
                  "fleetId": "1",
                  "rentedByUserId": null
                }
                """;
        mockMvc.perform(post("/api/v1/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vehicleJson))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("7. Role ADMIN -> GET /api/v1/users -> 200 OK")
    void whenAdminGetsAllUsers_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("8. Role ADMIN -> POST /api/v1/fleets -> 200 OK")
    void whenAdminCreatesFleet_thenOk() throws Exception {
        String fleetJson = """
                {
                  "id": "10",
                  "name": "West Side Fleet",
                  "address": "Shevchenka St. 42",
                  "capacity": 25
                }
                """;
        mockMvc.perform(post("/api/v1/fleets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fleetJson))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("9. Role ADMIN -> DELETE /api/v1/users/1 -> 403 Forbidden")
    void whenAdminDeletesUser_thenForbidden() throws Exception {
        mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "superadmin", roles = {"SUPERADMIN"})
    @DisplayName("10. Role SUPERADMIN -> POST & DELETE /api/v1/users/99 -> 200 OK")
    void whenSuperAdminCreatesAndDeletesUser_thenOk() throws Exception {
        String userJson = """
                {
                  "id": "99",
                  "email": "super.test@example.com",
                  "fullName": "Super Admin Test",
                  "driverLicense": "SA999999"
                }
                """;
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/v1/users/99"))
                .andExpect(status().isOk());
    }
}
