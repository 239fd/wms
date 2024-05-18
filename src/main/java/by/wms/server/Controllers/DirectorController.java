package by.wms.server.Controlles;

import by.wms.server.DTO.WarehouseDTO;
import by.wms.server.interfaces.WarehouseControl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/director")
public class DirectorController {


    @PostMapping("/create/warehouse")
    @PreAuthorize(value = "@cse.canAccessUser(#handlers)")
    public ResponseEntity<?>createWarehouse(@RequestBody WarehouseDTO warehouseDTO) {
        try{
        }
    }
}
