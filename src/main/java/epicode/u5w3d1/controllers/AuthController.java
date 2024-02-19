package epicode.u5w3d1.controllers;

import epicode.u5w3d1.entities.Employee;
import epicode.u5w3d1.payloads.EmploeeLoginDTO;
import epicode.u5w3d1.payloads.LoginResponseDTO;
import epicode.u5w3d1.payloads.NewEmployeeDTO;
import epicode.u5w3d1.services.AuthService;
import epicode.u5w3d1.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody EmploeeLoginDTO payload){
        return new LoginResponseDTO(authService.authenticateEmployeeAndGenerateToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(@RequestBody NewEmployeeDTO newEmployee) throws IOException {
        return this.employeeService.saveEmployee(newEmployee);
    }
}
