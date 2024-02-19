package epicode.u5w3d1.services;

import epicode.u5w3d1.entities.Employee;
import epicode.u5w3d1.exceptions.UnauthorizedException;
import epicode.u5w3d1.payloads.EmploeeLoginDTO;
import epicode.u5w3d1.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JWTTools jwtTools;

    public String authenticateEmployeeAndGenerateToken(EmploeeLoginDTO payload) {
        Employee employee = employeeService.findByEmail(payload.email());
        if (employee.getPassword().equals(payload.password())){
            return jwtTools.createToken(employee);
        }else {
            throw new UnauthorizedException("Email or pssw has been wrongfully added");
        }
    }
}
