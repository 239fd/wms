package by.wms.server.Repository;

import by.wms.server.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ProductRepository extends JpaRepository<Product, Integer> {
}
