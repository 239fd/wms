package by.wms.server.Controllers;

import by.wms.server.API.ApiResponse;
import by.wms.server.DTO.ProductDTO;
import by.wms.server.DTO.ShipDTO;
import by.wms.server.DTO.TableDTO;
import by.wms.server.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/worker")
@RequiredArgsConstructor
public class WorkerController {

    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<ApiResponse<TableDTO>> takeInfo(@RequestParam int userId) {

        List<TableDTO> tableDTOS = productService.takeInfo(userId);

        ApiResponse<TableDTO> response = ApiResponse.<TableDTO>builder()
                .data(tableDTOS)
                .status(true)
                .message("Information taken")
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ProductDTO>> takeProduct(@RequestParam int userId, @RequestBody List<ProductDTO> productDTO) {

        productService.addProductToCell(userId, productDTO);

        ApiResponse<ProductDTO> response = ApiResponse.<ProductDTO>builder()
                .data(productDTO)
                .status(true)
                .message("Taken")
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/ship")
    public ResponseEntity<ApiResponse<ShipDTO>> shipProduct(@RequestParam int userId, @RequestBody List<ShipDTO> shipDTO) {

        productService.shipProduct(userId, shipDTO);

        ApiResponse<ShipDTO> response = ApiResponse.<ShipDTO>builder()
                .data(shipDTO)
                .status(true)
                .message("Shipped")
                .build();

        return ResponseEntity.ok(response);

    }

}
