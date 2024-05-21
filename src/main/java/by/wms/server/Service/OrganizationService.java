package by.wms.server.Service;

import by.wms.server.DTO.OrganizationDTO;
import by.wms.server.Entity.Organization;
import by.wms.server.Repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public void  createOrganization(OrganizationDTO organizationDTO) {
        Organization organization = new Organization();
        organization.setINN(organizationDTO.getINN());
        organization.setName(organizationDTO.getName());
        organization.setAddress(organizationDTO.getAddress());
        organizationRepository.save(organization);
    }

    public String getOrganizationINN(String organizationINN) {
        Organization organization = organizationRepository.findByINN(organizationINN);
        return (organization != null) ? organization.getINN() : "Non-found";
    }

    public Organization getOrganizationByInn(String organizationINN) {
        return organizationRepository.getOrganizationByINN(organizationINN);

    }

    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }
}
