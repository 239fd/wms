package by.wms.server.Repository;

import by.wms.server.Entity.Cell;
import by.wms.server.Entity.Enum.Status;
import by.wms.server.Entity.Product;
import by.wms.server.Entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> getProductsByStatus(Status status);

    List<Product> getProductsByCellsAndStatus(Cell cell, Status status);

}
