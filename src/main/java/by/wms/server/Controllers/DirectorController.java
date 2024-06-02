package by.wms.server.Controllers;

import by.wms.server.API.ApiResponse;
import by.wms.server.DTO.*;
import by.wms.server.Entity.Product;
import by.wms.server.Entity.Rack;
import by.wms.server.Exceptions.AppException;
import by.wms.server.Service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@CrossOrigin(maxAge = 3600L)
@RestController
@RequestMapping("/api/v1/director")
@RequiredArgsConstructor
public class DirectorController {

    private final OrganizationService organizationService;
    private final WarehouseService warehouseService;
    private final RackService rackService;
    private final CellService cellService;
    private final ProductService productService;

    @PostMapping("/organization")
    public ResponseEntity<ApiResponse<OrganizationDTO>>createOrganization(@RequestBody OrganizationDTO organizationDTO) {
        String INN = organizationService.getOrganizationINN(organizationDTO.getINN());
        if (!Objects.equals(INN, "Non-found")) {
            throw new AppException("This INN is already taken", HttpStatus.NOT_FOUND);
        }
        organizationService.createOrganization(organizationDTO);

        ApiResponse<OrganizationDTO> response = ApiResponse.<OrganizationDTO>builder()
                .data(organizationDTO)
                .status(true)
                .message("Organization created successfully")
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/warehouse")
    public ResponseEntity<ApiResponse<WarehouseDTO>>createWarehouse(@RequestBody WarehouseDTO warehouseDTO) {
        String INN = organizationService.getOrganizationINN(warehouseDTO.getOrganizationINN());
        if (Objects.equals(INN, "Non-found")) {
            throw new AppException("No organization with this INN", HttpStatus.NOT_FOUND);
        }
        else if(warehouseService.findByNameAndOrganization(warehouseDTO.getName(), organizationService.getOrganizationByInn(warehouseDTO.getOrganizationINN())) ){
            throw new AppException("This name is used", HttpStatus.NOT_FOUND);
        }
        warehouseService.createWarehouse(warehouseDTO);
        ApiResponse<WarehouseDTO> response = ApiResponse.<WarehouseDTO>builder()
                .data(warehouseDTO)
                .status(true)
                .message("Warehouse created successfully" )
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/rack")
    public ResponseEntity<ApiResponse<RackAndCellDTO>>createRackAndCells(@RequestBody RackAndCellDTO rackAndCellDTO) {
        String INN = organizationService.getOrganizationINN(rackAndCellDTO.getOrganizationINN());

        if (Objects.equals(INN, "Non-found")) {
            throw new AppException("No organization with this INN", HttpStatus.NOT_FOUND);
        }
        else if(warehouseService.findById(rackAndCellDTO.getNumber())){
            throw new AppException("This warehouse's number is not found", HttpStatus.NOT_FOUND);
        }
        Rack rack = rackService.createRack(rackAndCellDTO, warehouseService.findByIdExtend(rackAndCellDTO.getNumber()));
        cellService.createCell(rackAndCellDTO, rack);
        ApiResponse<RackAndCellDTO> response = ApiResponse.<RackAndCellDTO>builder()
                .data(rackAndCellDTO)
                .status(true)
                .message("Rack created successfully")
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/docs")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getDocsInformation(
            @RequestParam Boolean accepted,
            @RequestParam Boolean writeoff,
            @RequestParam Boolean nonverified,
            @RequestParam int userId) {
        DocsDTO docsDTO = new DocsDTO(accepted, writeoff, nonverified);
        List<ProductDTO> products = productService.getAll(docsDTO, userId);

        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .data(products)
                .status(true)
                .message("Found")
                .build();

        return ResponseEntity.ok(response);
    }
}
