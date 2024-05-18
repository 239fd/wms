package by.wms.server.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeesDTO {
    private String login;
    private String password;
    private String title;
    private String firstName;
    private String secondName;
    private String surname;
    private String token;
}
