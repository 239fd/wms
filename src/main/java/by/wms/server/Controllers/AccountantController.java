package by.wms.server.Controllers;

import by.wms.server.API.ApiResponse;
import by.wms.server.DTO.*;
import by.wms.server.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600L)
@RestController
@RequestMapping("/api/v1/accountant")
@RequiredArgsConstructor
public class AccountantController {

    private final ProductService productService;

    @PutMapping("/inventory")
    public ResponseEntity<ApiResponse<List<InventoryDocsDTO>>> inventory(@RequestParam int userId, @RequestBody List<ShipDTO> shipDTO){

        productService.inventoryOfProduct(userId, shipDTO);

        List<InventoryDocsDTO> dto = productService.findAllFromShipDTO(userId, shipDTO);
        ApiResponse<List<InventoryDocsDTO>> response = ApiResponse.<List<InventoryDocsDTO>>builder()
                .data(dto)
                .status(true)
                .message("Inventory completed")
                .build();

        return ResponseEntity.ok(response);

    }

    @PutMapping("/writeoff")
    public ResponseEntity<ApiResponse<List<WriteOffActDTO>>> writeOff(@RequestParam int userId, @RequestBody List<WriteOffDTO> writeOffDTO) {

        productService.writeOff(userId, writeOffDTO);
        List<WriteOffActDTO> dto = productService.findAllFromWriteoffDTO(userId, writeOffDTO);


        ApiResponse<List<WriteOffActDTO>> response = ApiResponse.<List<WriteOffActDTO>>builder()
                .data(dto)
                .status(true)
                .message("Write Off completed")
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/revaluation")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> revaluate(@RequestParam int userId, @RequestBody List<RevaluationDTO> revaluationDTO){

        productService.revalueProduct(userId, revaluationDTO);
        List<ProductDTO> dto = productService.findAllFromDTO(userId, revaluationDTO);

        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .data(dto)
                .status(true)
                .message("Product revalued successfully")
                .build();

        return ResponseEntity.ok(response);
    }
}
