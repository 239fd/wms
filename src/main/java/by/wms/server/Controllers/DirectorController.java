package by.wms.server.Controllers;

import by.wms.server.DTO.OrganizationDTO;
import by.wms.server.DTO.WarehouseDTO;
import by.wms.server.Exceptions.AppException;
import by.wms.server.Service.OrganizationService;
import by.wms.server.Service.RackService;
import by.wms.server.Service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("api/v1/director")
@RequiredArgsConstructor
public class DirectorController {

    private final OrganizationService organizationService;
    private final WarehouseService warehouseService;
    private final RackService rackService;

    @PostMapping("/organization")
    public ResponseEntity<?>createOrganization(@RequestBody OrganizationDTO organizationDTO) {
        String INN = organizationService.getOrganizationINN(organizationDTO.getINN());
        if (!Objects.equals(INN, "Non-found")) {
            throw new AppException("This INN is already taken", HttpStatus.NOT_FOUND);
        }
        organizationService.createOrganization(organizationDTO);
        return ResponseEntity.ok(organizationDTO);
    }

    @PostMapping("/warehouse")
    public ResponseEntity<?>createWarehouse(@RequestBody WarehouseDTO warehouseDTO) {
        String INN = organizationService.getOrganizationINN(warehouseDTO.getOrganizationINN());
        if (Objects.equals(INN, "Non-found")) {
            throw new AppException("No organization with this INN", HttpStatus.NOT_FOUND);
        }
        else if(warehouseService.findByNameAndOrganization(warehouseDTO.getName(), organizationService.getOrganizationByInn(warehouseDTO.getOrganizationINN())) ){
            throw new AppException("This name is used", HttpStatus.NOT_FOUND);
        }
        warehouseService.createWarehouse(warehouseDTO);
        return ResponseEntity.ok(warehouseDTO);
    }


}
