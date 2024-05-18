package by.wms.server.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@RequiredArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private int id;
    private double length;
    private double width;
    private double height;
    private String name;
    private String unit;
    private int amount;
    private double price;
    private String status;
    private Date bestBeforeDate;

}
