package by.wms.server.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocsDTO {

    private Boolean accepted;
    private Boolean writeOff;
    private Boolean nonVerified;

}
