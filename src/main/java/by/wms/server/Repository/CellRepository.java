package by.wms.server.Repository;

import by.wms.server.Entity.Cell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CellRepository extends JpaRepository<Cell, Integer> {
    List<Cell> findByRackId(int rackId);
}

