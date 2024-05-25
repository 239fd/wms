package by.wms.server.Controllers;

import by.wms.server.API.ApiResponse;
import by.wms.server.DTO.CredentialsDTO;
import by.wms.server.DTO.EmployeesDTO;
import by.wms.server.DTO.SignUpDTO;
import by.wms.server.Service.EmployeesService;
import by.wms.server.config.UserAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Collections;

@RestController("api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final EmployeesService employeesService;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<EmployeesDTO>> login(@RequestBody CredentialsDTO credentialsDTO){
        EmployeesDTO employeesDTO = employeesService.login(credentialsDTO);

        employeesDTO.setToken(userAuthProvider.createToken(employeesDTO.getLogin()));
        ApiResponse<EmployeesDTO> response = ApiResponse.<EmployeesDTO>builder()
                .data(Collections.singletonList(employeesDTO))
                .status(true)
                .message("User successfully logged in")
                .build();

        return ResponseEntity.ok(response);
    }
    @PostMapping("/register")
    public ResponseEntity<EmployeesDTO> register(@RequestBody SignUpDTO signUpDTO) {
        EmployeesDTO employeesDTO = employeesService.register(signUpDTO);
        employeesDTO.setToken(userAuthProvider.createToken(signUpDTO.getLogin()));
        return ResponseEntity.created(URI.create("/users/" + employeesDTO.getLogin()))
                .body(employeesDTO);
    }
}
