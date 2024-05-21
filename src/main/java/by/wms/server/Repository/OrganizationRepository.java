package by.wms.server.Repository;

import by.wms.server.Entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {

    Organization findByINN(String INN);

    Organization getOrganizationByINN(String INN);

    Integer getOrganizationIdByINN(String INN);

}
