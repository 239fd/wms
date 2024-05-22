package by.wms.server.Controllers;

import by.wms.server.DTO.*;
import by.wms.server.Entity.Product;
import by.wms.server.Entity.Rack;
import by.wms.server.Exceptions.AppException;
import by.wms.server.Service.CellService;
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

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/director")
@RequiredArgsConstructor
public class DirectorController {

    private final OrganizationService organizationService;
    private final WarehouseService warehouseService;
    private final RackService rackService;
    private final CellService cellService;

    @PostMapping("/organization")
    public ResponseEntity<?>createOrganization(@RequestBody OrganizationDTO organizationDTO) {
        String INN = organizationService.getOrganizationINN(organizationDTO.getINN());
        if (!Objects.equals(INN, "Non-found")) {
            throw new AppException("This INN is already taken", HttpStatus.NOT_FOUND);
        }
        organizationService.createOrganization(organizationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
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
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/rack")
    public ResponseEntity<?>createRackAndCells(@RequestBody RackAndCellDTO rackAndCellDTO) {
        String INN = organizationService.getOrganizationINN(rackAndCellDTO.getOrganizationINN());
        if (Objects.equals(INN, "Non-found")) {
            throw new AppException("No organization with this INN", HttpStatus.NOT_FOUND);
        }
        else if(warehouseService.findById(rackAndCellDTO.getNumber())){
            throw new AppException("This warehouse's number is not found", HttpStatus.NOT_FOUND);
        }

        Rack rack = rackService.createRack(rackAndCellDTO, warehouseService.findByIdExtend(rackAndCellDTO.getNumber()));
        cellService.createCell(rackAndCellDTO, rack);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @GetMapping("/docs")
//    public ResponseEntity<?>getDocsInformation(@RequestBody DocsDTO docsDTO){
//        List<Product> products = productService.getAll(docsDTO);
//        return new ResponseEntity<>(products, HttpStatus.OK);
//    }
}
