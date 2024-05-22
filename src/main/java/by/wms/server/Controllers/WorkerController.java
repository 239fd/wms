package by.wms.server.Controllers;

import by.wms.server.DTO.ProductDTO;
import by.wms.server.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/worker")
@RequiredArgsConstructor
public class WorkerController {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> takeProduct(@RequestParam int userId, @RequestBody ProductDTO productDTO) {
        productService.addProductToCell(userId, productDTO);
        return ResponseEntity.ok(productDTO);
    }
}
