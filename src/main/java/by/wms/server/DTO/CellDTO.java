package by.wms.server.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class CellDTO {

    private double length;
    private double width;
    private double height;

}
