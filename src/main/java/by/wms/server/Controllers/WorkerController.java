package by.wms.server.Controllers;

import by.wms.server.DTO.ProductDTO;
import by.wms.server.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/worker")
@RequiredArgsConstructor
public class WorkerController {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> takeProduct( @RequestBody List<ProductDTO> productDTO) {
        System.out.println(productDTO.toString());
        productService.addProductToCell(25, productDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
