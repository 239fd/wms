package by.wms.server.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrganizationDTO {
    private String INN;
    private String name;
    private String address;
}
