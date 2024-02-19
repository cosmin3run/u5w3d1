package epicode.u5w3d1.payloads.errors;

import java.util.Date;
import java.util.List;

public record ErrorsPayloadWithList(String message,
                                    Date timestamp,
                                    List<String> errorsList) {
}
