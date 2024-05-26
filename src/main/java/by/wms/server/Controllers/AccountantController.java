package by.wms.server.Controllers;

import by.wms.server.API.ApiResponse;
import by.wms.server.DTO.ShipDTO;
import by.wms.server.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accountant")
@RequiredArgsConstructor
public class AccountantController {

    private final ProductService productService;

    @PutMapping("/inventory")
    public ResponseEntity<ApiResponse<ShipDTO>> inventory(@RequestParam int userId, @RequestBody List<ShipDTO> shipDTO){

        productService.inventoryOfProduct(userId, shipDTO);

        ApiResponse<ShipDTO> response = ApiResponse.<ShipDTO>builder()
                .data(shipDTO)
                .status(true)
                .message("Inventory completed")
                .build();

        return ResponseEntity.ok(response);

    }

}
