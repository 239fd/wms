package by.wms.server.DTO;

import by.wms.server.Entity.Enum.Status;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryDocsDTO {

    private String name;
    private Status status;


}
