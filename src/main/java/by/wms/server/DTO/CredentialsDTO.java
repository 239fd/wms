package by.wms.server.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CredentialsDTO {

    private String login;
    private char[] password;
}
