package by.wms.server.Service;

import by.wms.server.DTO.DocsDTO;
import by.wms.server.DTO.ProductDTO;
import by.wms.server.DTO.ShipDTO;
import by.wms.server.DTO.TableDTO;
import by.wms.server.Entity.*;
import by.wms.server.Entity.Enum.Status;
import by.wms.server.Exceptions.AppException;
import by.wms.server.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final RackRepository rackRepository;
    private final CellRepository cellRepository;

    public void addProductToCell(int userId, List<ProductDTO> productDTOs) {

        List<Product> products = new ArrayList<>();

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
                            products.add(product);
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

    public List<Product> getAll(DocsDTO docsDTO, int userId) {

        Warehouse warehouse = warehouseRepository.getWarehouseByEmployeesId(userId);
        if (warehouse == null) {
            throw new AppException("No warehouse found for user ID: " + userId, HttpStatus.CONFLICT);
        }

        List<Rack> racks = rackRepository.findByWarehouseId(warehouse.getId());
        if (racks == null || racks.isEmpty()) {
            throw new AppException("No racks found for warehouse ID: " + warehouse.getId(), HttpStatus.CONFLICT);
        }

        List<Product> products = new ArrayList<>();
        for (Rack rack : racks) {
            List<Cell> cells = cellRepository.findByRackId(rack.getId());
            for (Cell cell : cells) {
                if (docsDTO.getAccepted()) {
                    products.addAll(productRepository.getProductsByCellsAndStatus(cell, Status.accepted));
                }
                if (docsDTO.getWriteOff()) {
                    products.addAll(productRepository.getProductsByCellsAndStatus(cell, Status.writeoff));
                }
                if (docsDTO.getNonVerified()) {
                    products.addAll(productRepository.getProductsByCellsAndStatus(cell, Status.nonverified));
                }
            }
        }

        if (products.isEmpty()) {
            throw new AppException("No products found for the given organization", HttpStatus.NOT_FOUND);
        }

        return products;
    }

    public List<TableDTO> takeInfo(int userId) {

        Warehouse warehouse = warehouseRepository.getWarehouseByEmployeesId(userId);
        if (warehouse == null) {
            throw new AppException("No warehouse found for user ID: " + userId, HttpStatus.CONFLICT);
        }

        List<Rack> racks = rackRepository.findByWarehouseId(warehouse.getId());
        if (racks == null || racks.isEmpty()) {
            throw new AppException("No racks found for warehouse ID: " + warehouse.getId(), HttpStatus.CONFLICT);
        }

        List<TableDTO> productInfoList = new ArrayList<>();

        for (Rack rack : racks) {
            List<Cell> cells = cellRepository.findByRackId(rack.getId());

            for (Cell cell : cells) {
                for (Product product : cell.getProducts()) {
                    TableDTO productInfo = TableDTO.builder()
                            .number(product.getId())
                            .name(product.getName())
                            .date(product.getBestBeforeDate())
                            .build();
                    productInfoList.add(productInfo);
                }
            }
        }
        return productInfoList;

    }

    public void shipProduct (int userId, List<ShipDTO> shipDTOs){

        Warehouse warehouse = warehouseRepository.getWarehouseByEmployeesId(userId);
        if (warehouse == null) {
            throw new AppException("No warehouse found for user ID: " + userId, HttpStatus.CONFLICT);
        }

        List<Rack> racks = rackRepository.findByWarehouseId(warehouse.getId());
        if (racks == null || racks.isEmpty()) {
            throw new AppException("No racks found for warehouse ID: " + warehouse.getId(), HttpStatus.CONFLICT);
        }

        for (ShipDTO shipDTO : shipDTOs) {
            boolean productRemoved = false;

            for (Rack rack : racks) {
                List<Cell> cells = cellRepository.findByRackId(rack.getId());

                for (Cell cell : cells) {
                    for (Product product : cell.getProducts()) {
                        if (product.getId() == shipDTO.getNumber()) {
                            cell.getProducts().remove(product);
                            productRepository.delete(product);
                            cellRepository.save(cell);

                            productRemoved = true;
                            break;
                        }
                    }
                    if (productRemoved) {
                        break;
                    }
                }
                if (productRemoved) {
                    break;
                }
            }

            if (!productRemoved) {
                throw new AppException("Product with ID: " + shipDTO.getNumber() + " not found", HttpStatus.BAD_REQUEST);
            }
        }
    }
}
