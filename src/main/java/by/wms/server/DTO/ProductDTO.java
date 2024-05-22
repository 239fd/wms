package by.wms.server.DTO;

import by.wms.server.Entity.Enum.Status;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@RequiredArgsConstructor
@Getter
@Setter
public class ProductDTO {

    private int amountOfPosition;
    private double length;
    private double width;
    private double height;
    private String name;
    private String unit;
    private int amount;
    private double price;
    private Status status;
    private Date bestBeforeDate;
    private double weight;

}
