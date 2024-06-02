package by.wms.server.DTO;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TableDTO {

    private int number;
    private String name;
    private int amount;

}
