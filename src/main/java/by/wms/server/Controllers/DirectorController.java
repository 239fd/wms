package by.wms.server.Controllers;

import by.wms.server.DTO.CellDTO;
import by.wms.server.DTO.WarehouseDTO;
import by.wms.server.Service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/director")
public class DirectorController {

    private final DirectorService directorService;

    @PostMapping("/create/warehouse")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?>createWarehouse(@RequestBody WarehouseDTO warehouseDTO) {
        return new ResponseEntity<>(directorService.create(warehouseDTO), HttpStatus.CREATED);
    }

    @PostMapping("/create/structure")
    public ResponseEntity<?>createCell(@RequestBody CellDTO cellDTO) {
        return new ResponseEntity<>(directorService.create(cellDTO), HttpStatus.CREATED);
    }
}
