package by.wms.server.Controllers;

import by.wms.server.API.ApiResponse;
import by.wms.server.DTO.ProductDTO;
import by.wms.server.DTO.ShipDTO;
import by.wms.server.DTO.TableDTO;
import by.wms.server.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600L)
@RestController
@RequestMapping("/api/v1/worker")
@RequiredArgsConstructor
public class WorkerController {

    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<ApiResponse<List<TableDTO>>> takeInfo(@RequestParam int userId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        List<TableDTO> tableDTOS =  productService.takeInfo(userId);

        ApiResponse<List<TableDTO>> response = ApiResponse.<List<TableDTO>>builder()
                .data(tableDTOS)
                .status(true)
                .message("Information taken")
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> takeProduct(@RequestParam int userId, @RequestBody List<ProductDTO> productDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        productService.addProductToCell(userId, productDTO);

        ApiResponse<List<ProductDTO>> response = ApiResponse.<List<ProductDTO>>builder()
                .data(productDTO)
                .status(true)
                .message("Taken")
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/ship")
    public ResponseEntity<ApiResponse<List<ShipDTO>>> shipProduct(@RequestParam int userId, @RequestBody List<ShipDTO> shipDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        productService.shipProduct(userId, shipDTO);

        ApiResponse<List<ShipDTO>> response = ApiResponse.<List<ShipDTO>>builder()
                .data(shipDTO)
                .status(true)
                .message("Shipped")
                .build();

        return ResponseEntity.ok(response);

    }
}
