package by.wms.server.Service;

import by.wms.server.DTO.RackAndCellDTO;
import by.wms.server.Entity.Rack;
import by.wms.server.Entity.Warehouse;
import by.wms.server.Repository.RackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RackService {

    private final RackRepository rackRepository;

    public Rack createRack(RackAndCellDTO rackAndCellDTO, Warehouse warehouse) {
        Rack rack = new Rack();
        rack.setCapacity(rackAndCellDTO.getCapacity());
        rack.setWarehouse(warehouse);
        rackRepository.save(rack);
        return rack;
    }

}
