package by.wms.server.Service;

import by.wms.server.DTO.WarehouseDTO;
import by.wms.server.Entity.Organization;
import by.wms.server.Entity.Warehouse;
import by.wms.server.Repository.OrganizationRepository;
import by.wms.server.Repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WarehouseService {

    private final OrganizationRepository repository;
    private final WarehouseRepository warehouseRepository;

    public void createWarehouse(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = new Warehouse();
        Organization organization = repository.getOrganizationByINN(warehouseDTO.getOrganizationINN());
        warehouse.setOrganization(organization);
        warehouse.setName(warehouseDTO.getName());
        warehouse.setAddress(warehouseDTO.getAddress());
        warehouseRepository.save(warehouse);

    }
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }
    public int getWarehouseIdByOrganizationINN(String organizationId) {
        return warehouseRepository.getWarehouseIdByOrganizationINN(organizationId);
    }

    public boolean findByNameAndOrganization(String name, Organization organization) {
        return warehouseRepository.findByNameAndOrganization(name, organization) != null;
    }
    public boolean findById(int id) {
        return warehouseRepository.findWarehouseById(id) == null;
    }
    public Warehouse findByIdExtend(int id) {
        return warehouseRepository.findWarehouseById(id);
    }
}
