package epicode.u5w3d1.payloads;

import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record AssignDeviceToEmloyeeDTO(
        @NotEmpty(message = "You need to set the EmployeeID")
        UUID id
) {
}
