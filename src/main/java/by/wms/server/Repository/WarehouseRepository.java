package by.wms.server.Repository;

import by.wms.server.Entity.Organization;
import by.wms.server.Entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {

    Warehouse findByNameAndOrganization(String name, Organization organization);

    Warehouse findWarehouseById(Integer id);

    Warehouse getWarehouseById(Integer id);

    Warehouse getWarehouseByEmployeesId(Integer id);

}
