package by.wms.server.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocsDTO {

    private boolean accepted;
    private boolean writeOff;
    private boolean nonVerified;

}
