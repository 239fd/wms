package by.wms.server.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SignUpDTO {

    private String login;
    private String password;
    private String title;
    private String firstName;
    private String secondName;
    private String surname;

}
