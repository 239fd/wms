package by.wms.server.Service;

import by.wms.server.DTO.ProductDTO;
import by.wms.server.Entity.*;
import by.wms.server.Entity.Enum.Status;
import by.wms.server.Exceptions.AppException;
import by.wms.server.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final RackRepository rackRepository;
    private final CellRepository cellRepository;

    public void addProductToCell(int userId, List<ProductDTO> productDTOs) {

        Warehouse warehouse = warehouseRepository.getWarehouseByEmployeesId(userId);
        if (warehouse == null) {
            throw new AppException("No warehouse found for user ID: " + userId, HttpStatus.CONFLICT);
        }
        List<Rack> racks = rackRepository.findByWarehouseId(warehouse.getId());
        if (racks == null) {
            throw new AppException("No racks found for user ID: " + userId, HttpStatus.CONFLICT);
        }

        for (ProductDTO productDTO : productDTOs) {
            boolean productPlaced = false;

            for (Rack rack : racks) {
                double usedCapacity = 0;
                List<Cell> cells = cellRepository.findByRackId(rack.getId());

                for (Cell cell : cells) {
                    for (Product product : cell.getProducts()) {
                        usedCapacity += product.getWeight();
                    }
                }

                double availableCapacity = rack.getCapacity() - usedCapacity;

                if (availableCapacity >= productDTO.getWeight()) {
                    for (Cell cell : cells) {
                        if (cell.getLength() >= productDTO.getLength() &&
                                cell.getWidth() >= productDTO.getWidth() &&
                                cell.getHeight() >= productDTO.getHeight()) {

                            Product product = new Product();
                            product.setName(productDTO.getName());
                            product.setAmount(productDTO.getAmount());
                            product.setHeight(productDTO.getHeight());
                            product.setLength(productDTO.getLength());
                            product.setWidth(productDTO.getWidth());
                            product.setWeight(productDTO.getWeight());
                            product.setPrice(productDTO.getPrice());
                            product.setUnit(productDTO.getUnit());
                            product.setBestBeforeDate(productDTO.getBestBeforeDate());
                            product.setStatus(Status.valueOf(String.valueOf(productDTO.getStatus())));

                            product.getCells().add(cell);
                            productRepository.save(product);

                            cell.getProducts().add(product);
                            cellRepository.save(cell);

                            productPlaced = true;
                            break;
                        }
                    }

                    if (productPlaced) {
                        break;
                    }
                }
            }

            if (!productPlaced) {
                throw new AppException("No suitable rack or cell found for product: " + productDTO.getName(), HttpStatus.BAD_REQUEST);
            }
        }
    }

}
