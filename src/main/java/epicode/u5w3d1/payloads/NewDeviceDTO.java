package epicode.u5w3d1.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record NewDeviceDTO(

        @NotEmpty(message = "Please insert type")
        @NotBlank
        String type


) {
}
