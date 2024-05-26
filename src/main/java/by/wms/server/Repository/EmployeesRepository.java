package by.wms.server.Repository;

import by.wms.server.Entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeesRepository extends JpaRepository<Employees, Integer> {

    Optional<Employees> findByLogin(String login);

}
