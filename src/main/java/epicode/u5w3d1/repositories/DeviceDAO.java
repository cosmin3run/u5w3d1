package epicode.u5w3d1.repositories;

import cosminpetrea.u5w2d5.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeviceDAO extends JpaRepository<Device, UUID> {
}
