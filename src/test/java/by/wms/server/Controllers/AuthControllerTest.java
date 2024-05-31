package by.wms.server.Controllers;

import by.wms.server.API.ApiResponse;
import by.wms.server.DTO.CredentialsDTO;
import by.wms.server.DTO.EmployeesDTO;
import by.wms.server.DTO.SignUpDTO;
import by.wms.server.Service.EmployeesService;
import by.wms.server.config.UserAuthProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeesService employeesService;

    @MockBean
    private UserAuthProvider userAuthProvider;

    @Autowired
    private ObjectMapper objectMapper;

    private EmployeesDTO employeesDTO;
    private SignUpDTO signUpDTO;
    private CredentialsDTO credentialsDTO;

    @BeforeEach
    void setUp() {
        employeesDTO = new EmployeesDTO();
        employeesDTO.setLogin("pavel");
        employeesDTO.setToken("pavel");

        signUpDTO = new SignUpDTO();
        signUpDTO.setLogin("pavel");
        signUpDTO.setPassword("pavel");

        credentialsDTO = new CredentialsDTO();
        credentialsDTO.setLogin("pavel");
        credentialsDTO.setPassword("pavel");
    }

    @Test
    void login_ShouldReturnEmployeeDTO() throws Exception {
        when(employeesService.login(any(CredentialsDTO.class))).thenReturn(employeesDTO);
        when(userAuthProvider.createToken(any(String.class))).thenReturn("testToken");

        mockMvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(credentialsDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"data\":{\"login\":\"testUser\",\"token\":\"testToken\"},\"status\":true,\"message\":\"User successfully logged in\"}"));
    }

    @Test
    void register_ShouldReturnSignUpDTO() throws Exception {
        when(employeesService.register(any(SignUpDTO.class))).thenReturn(employeesDTO);
        when(userAuthProvider.createToken(any(String.class))).thenReturn("testToken");

        mockMvc.perform(post("/api/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"data\":{\"login\":\"newUser\",\"password\":\"password\"},\"status\":true,\"message\":\"User successfully logged in\"}"));
    }
}
