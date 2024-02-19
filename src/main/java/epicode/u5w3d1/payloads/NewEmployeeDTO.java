package epicode.u5w3d1.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NewEmployeeDTO(
        @NotEmpty(message = "Username is mandatory")
        @Size(min = 3, max = 20, message = "Username should have at least 3 and maximum 20 characters")
        String username,

        @NotEmpty(message = "Name is mandatory")
        @Size(min = 3, max = 20, message = "Name should have at least 3 and maximum 20 characters")
        String name,


        @NotEmpty(message = "Name is mandatory")
        @Size(min = 3, max = 20, message = "Surname should have at least 3 and maximum 20 characters")
        String surname,

        @NotEmpty(message = "Email is mandatory")
        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "L'email inserita non è valida")
        String email) {




}
