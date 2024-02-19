package epicode.u5w3d1.repositories;

import cosminpetrea.u5w2d5.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeDAO extends JpaRepository<Employee, UUID> {
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByUsername(String username);
}
