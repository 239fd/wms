package by.wms.server.Service;

import by.wms.server.DTO.*;
import by.wms.server.Entity.*;
import by.wms.server.Entity.Enum.Status;
import by.wms.server.Exceptions.AppException;
import by.wms.server.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final RackRepository rackRepository;
    private final CellRepository cellRepository;

    public List<ProductDTO> findAllFromDTO(int userId, List<RevaluationDTO> dto){
        Warehouse warehouse = warehouseRepository.getWarehouseByEmployeesId(userId);
        if (warehouse == null) {
            throw new AppException("No warehouse found for user ID: " + userId, HttpStatus.CONFLICT);
        }

        List<Rack> racks = rackRepository.findByWarehouseId(warehouse.getId());
        if (racks == null || racks.isEmpty()) {
            throw new AppException("No racks found for warehouse ID: " + warehouse.getId(), HttpStatus.CONFLICT);
        }

        List<ProductDTO> dtos = new ArrayList<>();

        for (RevaluationDTO revaluationDTO : dto) {
            Product product = productRepository.findAllById(revaluationDTO.getNumber());
            ProductDTO dto1 = new ProductDTO(product.getLength(), product.getWidth(), product.getHeight(), product.getName(), product.getUnit(), product.getAmount(), product.getPrice(), product.getStatus(), product.getWeight());
            dtos.add(dto1);
        }

        return dtos;
    }

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

    public List<ProductDTO> getAll(DocsDTO docsDTO, int userId) {

        List<Product> products = new ArrayList<>();
        List<ProductDTO> productDTOs = new ArrayList<>();

        Warehouse warehouse = warehouseRepository.getWarehouseByEmployeesId(userId);
        if (warehouse == null) {
            throw new AppException("No warehouse found for user ID: " + userId, HttpStatus.CONFLICT);
        }
        System.out.println("1");
        List<Rack> racks = rackRepository.findByWarehouseId(warehouse.getId());
        if (racks == null || racks.isEmpty()) {
            throw new AppException("No racks found for warehouse ID: " + warehouse.getId(), HttpStatus.CONFLICT);
        }
        System.out.println("2");

        for (Rack rack : racks) {
            List<Cell> cells = cellRepository.findByRackId(rack.getId());
            for (Cell cell : cells) {
                if (docsDTO.getAccepted()) {
                    products.addAll(productRepository.getProductsByCellsAndStatus(cell, Status.accepted));
                    for(int i = 0; i < products.toArray().length; i++){
                        ProductDTO productInfo = ProductDTO.builder()
                                .name(products.get(i).getName())
                                .unit(products.get(i).getUnit())
                                .status(products.get(i).getStatus())
                                .amount(products.get(i).getAmount())
                                .price(products.get(i).getPrice())
                                .length(products.get(i).getLength())
                                .width(products.get(i).getWidth())
                                .height(products.get(i).getHeight())
                                .weight(products.get(i).getWeight())
                                .build();
                        productDTOs.add(productInfo);
                    }
                }
                if (docsDTO.getWriteOff()) {
                    products.addAll(productRepository.getProductsByCellsAndStatus(cell, Status.writeoff));
                    for(int i = 0; i < products.toArray().length; i++){
                        ProductDTO productInfo = ProductDTO.builder()
                                .name(products.get(i).getName())
                                .unit(products.get(i).getUnit())
                                .status(products.get(i).getStatus())
                                .amount(products.get(i).getAmount())
                                .price(products.get(i).getPrice())
                                .length(products.get(i).getLength())
                                .width(products.get(i).getWidth())
                                .height(products.get(i).getHeight())
                                .weight(products.get(i).getWeight())
                                .build();
                        productDTOs.add(productInfo);
                    }
                }
                if (docsDTO.getNonVerified()) {
                    products.addAll(productRepository.getProductsByCellsAndStatus(cell, Status.nonverified));
                    for(int i = 0; i < products.toArray().length; i++){
                        ProductDTO productInfo = ProductDTO.builder()
                                .name(products.get(i).getName())
                                .unit(products.get(i).getUnit())
                                .status(products.get(i).getStatus())
                                .amount(products.get(i).getAmount())
                                .price(products.get(i).getPrice())
                                .length(products.get(i).getLength())
                                .width(products.get(i).getWidth())
                                .height(products.get(i).getHeight())
                                .weight(products.get(i).getWeight())
                                .build();
                        productDTOs.add(productInfo);
                    }
                }
            }
        }

        if (products.isEmpty()) {
            throw new AppException("No products found for the given organization", HttpStatus.NOT_FOUND);
        }

        return productDTOs;
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
                            .amount(product.getAmount())
                            .build();
                    productInfoList.add(productInfo);
                }
            }
        }
        return productInfoList;

    }

    public void shipProduct(int userId, List<ShipDTO> shipDTOs) {

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

    public void inventoryOfProduct(int userId, List<ShipDTO> shipDTOs) {

        Warehouse warehouse = warehouseRepository.getWarehouseByEmployeesId(userId);
        if (warehouse == null) {
            throw new AppException("No warehouse found for user ID: " + userId, HttpStatus.NOT_FOUND);
        }

        List<Rack> racks = rackRepository.findByWarehouseId(warehouse.getId());
        Set<Product> productsInWarehouse = new HashSet<>();

        for (Rack rack : racks) {
            List<Cell> cells = cellRepository.findByRackId(rack.getId());
            for (Cell cell : cells) {
                productsInWarehouse.addAll(cell.getProducts());
            }
        }

        Set<Integer> shipProductNumbers = new HashSet<>();
        for (ShipDTO shipDTO : shipDTOs) {
            shipProductNumbers.add(shipDTO.getNumber());
        }

        for (Product product : productsInWarehouse) {
            if (shipProductNumbers.contains(product.getId())) {
                product.setStatus(Status.accepted);
            } else {
                product.setStatus(Status.nonverified);
            }
            productRepository.save(product);
        }

    }

    public void writeOff(int userId, List<WriteOffDTO> writeOffDTOS) {

        Warehouse warehouse = warehouseRepository.getWarehouseByEmployeesId(userId);
        if (warehouse == null) {
            throw new AppException("No warehouse found for user ID: " + userId, HttpStatus.NOT_FOUND);
        }

        List<Rack> racks = rackRepository.findByWarehouseId(warehouse.getId());
        Set<Product> productsInWarehouse = new HashSet<>();

        for (Rack rack : racks) {
            List<Cell> cells = cellRepository.findByRackId(rack.getId());
            for (Cell cell : cells) {
                productsInWarehouse.addAll(cell.getProducts());
            }
        }

        Set<Integer> writeOffProductNumbers = new HashSet<>();
        for (WriteOffDTO writeOffDTO : writeOffDTOS) {
            writeOffProductNumbers.add(writeOffDTO.getNumber());
        }

        for (Product product : productsInWarehouse) {
            product.setStatus(Status.writeoff);
            productRepository.save(product);
        }

    }

    public void revalueProduct(int userId, List<RevaluationDTO> revaluationDTOs) {

        Warehouse warehouse = warehouseRepository.getWarehouseByEmployeesId(userId);
        if (warehouse == null) {
            throw new AppException("No warehouse found for user ID: " + userId, HttpStatus.CONFLICT);
        }

        for (RevaluationDTO dto : revaluationDTOs) {
            boolean productFound = false;

            for (Rack rack : warehouse.getRacks()) {
                for (Cell cell : rack.getCells()) {
                    for (Product product : cell.getProducts()) {
                        if (product.getId() == dto.getNumber()) {
                            productFound = true;
                            break;
                        }
                    }
                    if (productFound) break;
                }
                if (productFound) break;
            }

            if (!productFound) {
                throw new AppException("Product with number " + dto.getNumber() + " not found in warehouse for user ID: " + userId, HttpStatus.BAD_REQUEST);
            }
        }
        for (RevaluationDTO dto : revaluationDTOs) {
            boolean productFound = false;

            for (Rack rack : warehouse.getRacks()) {
                for (Cell cell : rack.getCells()) {
                    for (Product product : cell.getProducts()) {
                        if (product.getId() == dto.getNumber()) {
                            product.setPrice(dto.getCost());
                            productRepository.save(product);
                            productFound = true;
                            break;
                        }
                    }
                    if (productFound) break;
                }
                if (productFound) break;
            }

            if (!productFound) {
                throw new AppException("Product with number " + dto.getNumber() + " not found in warehouse for user ID: " + userId, HttpStatus.BAD_REQUEST);
            }
        }
    }
}
