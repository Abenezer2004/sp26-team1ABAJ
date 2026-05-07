package NurseSearch.backendAPI.controller;

import NurseSearch.backendAPI.dto.LoginRequest;
import NurseSearch.backendAPI.entity.Customer;
import NurseSearch.backendAPI.entity.Nurse;
import NurseSearch.backendAPI.service.CustomerService;
import NurseSearch.backendAPI.service.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private NurseService nurseService;

    @Autowired
    private CustomerService customerService;

    // Provider login. Kept for your existing provider-side testing.
    @PostMapping("/login")
    public ResponseEntity<?> loginNurse(@RequestBody LoginRequest request) {
        try {
            Nurse nurse = nurseService.loginNurse(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(userResponse("Login successful", nurse.getUserId(), nurse.getEmail(),
                    nurse.getFirstName(), nurse.getLastName(), nurse.getRole().name(),
                    nurse.getLoginCount(), nurse.getLastLoginAt()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping("/nurse/login")
    public ResponseEntity<?> loginNurseExplicit(@RequestBody LoginRequest request) {
        return loginNurse(request);
    }

    @PostMapping("/customer/login")
    public ResponseEntity<?> loginCustomer(@RequestBody LoginRequest request) {
        try {
            Customer customer = customerService.loginCustomer(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(userResponse("Login successful", customer.getUserId(), customer.getEmail(),
                    customer.getFirstName(), customer.getLastName(), customer.getRole().name(),
                    customer.getLoginCount(), customer.getLastLoginAt()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    private Map<String, Object> userResponse(String message, Long userId, String email,
                                             String firstName, String lastName, String role,
                                             Integer loginCount, Object lastLoginAt) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("userId", userId);
        response.put("email", email);
        response.put("firstName", firstName);
        response.put("lastName", lastName);
        response.put("role", role);
        response.put("loginCount", loginCount);
        response.put("lastLoginAt", lastLoginAt);
        return response;
    }
}
