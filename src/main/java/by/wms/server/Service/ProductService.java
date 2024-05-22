package by.wms.server.Service;

import by.wms.server.DTO.ProductDTO;
import by.wms.server.Entity.*;
import by.wms.server.Exceptions.AppException;
import by.wms.server.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final RackRepository rackRepository;
    private final CellRepository cellRepository;
    private final EmployeesRepository employeesRepository;

    public void addProductToCell(int userId, ProductDTO productDTO) {

        Warehouse warehouse = warehouseRepository.getWarehouseByEmployeesId(userId);

        List<Rack> racks = rackRepository.findByWarehouseId(warehouse.getId());

            for (Rack rack : racks) {

                if (rack.getCapacity() >= productDTO.getWeight()) {

                    List<Cell> cells = cellRepository.findByRackId(rack.getId());

                    // Цикл по ячейкам
                    for (Cell cell : cells) {
                        // Проверка вместимости ячейки по габаритам
                        if (cell.getLength() >= productDTO.getLength() &&
                                cell.getWidth() >= productDTO.getWidth() &&
                                cell.getHeight() >= productDTO.getHeight()) {

                            // Добавление продукта в ячейку
                            Product product = new Product();
                            product.setName(productDTO.getName());
                            product.setAmount(productDTO.getAmount());
                            product.setHeight(productDTO.getHeight());
                            product.setLength(productDTO.getLength());
                            product.setPrice(productDTO.getPrice());
                            product.setUnit(productDTO.getUnit());
                            product.setWidth(productDTO.getWidth());
                            product.setBestBeforeDate(productDTO.getBestBeforeDate());
                            product.setStatus(productDTO.getStatus());
                            productRepository.save(product);

                            // Логика добавления продукта в ячейку (например, создание записи в таблице cell_has_product)

                            return; // Продукт добавлен, завершаем метод
                        }
                    }
                }
            }

        throw new AppException("No suitable cell found", HttpStatus.BAD_REQUEST);
    }

}
