package by.wms.server.DTO;

import lombok.*;

@Data
@Getter
@RequiredArgsConstructor
@Setter
@AllArgsConstructor
public class RevaluationDTO {

    private int numberOfAgreement;
    private int amount;
    private int number;
    private double cost;

}
