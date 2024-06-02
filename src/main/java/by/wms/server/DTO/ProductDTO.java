package by.wms.server.DTO;

import by.wms.server.Entity.Enum.Status;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProductDTO {

    private double length;
    private double width;
    private double height;
    private String name;
    private String unit;
    private int amount;
    private double price;
    private Status status;
    private double weight;

}
