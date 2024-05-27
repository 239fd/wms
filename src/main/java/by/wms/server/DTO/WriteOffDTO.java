package by.wms.server.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class WriteOffDTO {

    private int number;
    private String reason;
}
