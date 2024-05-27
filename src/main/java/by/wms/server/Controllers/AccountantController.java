package by.wms.server.Controllers;

import by.wms.server.API.ApiResponse;
import by.wms.server.DTO.RevaluationDTO;
import by.wms.server.DTO.ShipDTO;
import by.wms.server.DTO.TableDTO;
import by.wms.server.DTO.WriteOffDTO;
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
    public ResponseEntity<ApiResponse<List<ShipDTO>>> inventory(@RequestParam int userId, @RequestBody List<ShipDTO> shipDTO){

        productService.inventoryOfProduct(userId, shipDTO);

        ApiResponse<List<ShipDTO>> response = ApiResponse.<List<ShipDTO>>builder()
                .data(shipDTO)
                .status(true)
                .message("Inventory completed")
                .build();

        return ResponseEntity.ok(response);

    }

    @GetMapping("/revaluation")
    public ResponseEntity<ApiResponse<List<TableDTO>>> takeInfo(@RequestParam int userId) {

        List<TableDTO> tableDTOS = productService.takeInfo(userId);

        ApiResponse<List<TableDTO>> response = ApiResponse.<List<TableDTO>>builder()
                .data(tableDTOS)
                .status(true)
                .message("Information taken")
                .build();

        return ResponseEntity.ok(response);

    }

    @PutMapping("/writeoff")
    public ResponseEntity<ApiResponse<List<WriteOffDTO>>> writeOff(@RequestParam int userId, @RequestBody List<WriteOffDTO> writeOffDTO) {

        productService.writeOff(userId, writeOffDTO);

        ApiResponse<List<WriteOffDTO>> response = ApiResponse.<List<WriteOffDTO>>builder()
                .data(writeOffDTO)
                .status(true)
                .message("Write Off completed")
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/revaluation")
    public ResponseEntity<ApiResponse<List<RevaluationDTO>>> revaluate(@RequestParam int userId, @RequestBody List<RevaluationDTO> revaluationDTO){

        productService.revalueProduct(userId, revaluationDTO);

        ApiResponse<List<RevaluationDTO>> response = ApiResponse.<List<RevaluationDTO>>builder()
                .data(revaluationDTO)
                .status(true)
                .message("Product revalued successfully")
                .build();

        return ResponseEntity.ok(response);
    }
}
