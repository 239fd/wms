package by.wms.server.Service;

import by.wms.server.DTO.CellDTO;
import by.wms.server.DTO.WarehouseDTO;
import by.wms.server.Entity.Cell;
import by.wms.server.Entity.Warehouse;
import by.wms.server.Repository.CellRepository;
import by.wms.server.Repository.WarehouseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class DirectorService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private CellRepository cellRepository;

    public Warehouse create(WarehouseDTO dto){
        Warehouse warehouse = new Warehouse(dto.getId(), dto.getName(), dto.getAddress());
        return warehouseRepository.save(warehouse);
    }



    public Cell create(CellDTO dto){
        Cell cell = new Cell(dto.getId(), dto.getLength(), dto.getHeight(), dto.getWidth());
        return cellRepository.save(cell);
    }


}