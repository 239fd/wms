package by.wms.server.Repository;

import by.wms.server.Entity.Rack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RackRepository extends JpaRepository<Rack, Integer> {
    List<Rack> findByWarehouseId(int warehouseId);
}

