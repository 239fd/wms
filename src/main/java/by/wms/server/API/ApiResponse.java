package by.wms.server.API;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class ApiResponse<T> {

    private List<T> data;
    private boolean status;
    private String message;

}
