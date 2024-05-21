package by.wms.server.Service;

import by.wms.server.DTO.RackAndCellDTO;
import by.wms.server.Entity.Cell;
import by.wms.server.Entity.Rack;
import by.wms.server.Repository.CellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CellService {

    private final CellRepository cellRepository;

    public void createCell(RackAndCellDTO rackAndCellDTO, Rack rack){
        int amount = rackAndCellDTO.getAmount();
        for(int i = 0; i<amount; i++){
            Cell cell = new Cell();
            cell.setRack(rack);
            cell.setLength(rackAndCellDTO.getLength());
            cell.setWidth(rackAndCellDTO.getWidth());
            cell.setHeight(rackAndCellDTO.getHeight());
            cellRepository.save(cell);
        }
    }

}
