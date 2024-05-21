package by.wms.server.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class RackAndCellDTO {

    private String organizationINN;
    private int number;
    private int capacity;
    private int amount;
    private double length;
    private double width;
    private double height;

}
