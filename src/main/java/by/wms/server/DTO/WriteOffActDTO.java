package by.wms.server.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WriteOffActDTO {

    private String name;
    private String unit;
    private int amount;
    private double price;
    private String reason;


}
