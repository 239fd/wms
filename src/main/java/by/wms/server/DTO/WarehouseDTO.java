package by.wms.server.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class WarehouseDTO {
    private int id;
    private String name;
    private String address;
}
