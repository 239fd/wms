package by.wms.server.Controllers;

import by.wms.server.API.ApiResponse;
import by.wms.server.DTO.ProductDTO;
import by.wms.server.DTO.ShipDTO;
import by.wms.server.DTO.TableDTO;
import by.wms.server.Service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WorkerController.class)
public class WorkerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String AUTH_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJkaXJlY3RvciIsImV4cCI6MTcxNjY4NTQ3MywiaWF0IjoxNzE2NjgxODczfQ.MgmUJ1FGx86nMJT5FZtcCq2b3fIC63H8foW5f3dJKRI";

    @Test
    public void testTakeInfo() throws Exception {
        List<TableDTO> tableDTOS = Collections.singletonList(new TableDTO(1, "Product", null));
        when(productService.takeInfo(anyInt())).thenReturn(tableDTOS);

        mockMvc.perform(get("/api/v1/worker")
                        .param("userId", "1")
                        .header(HttpHeaders.AUTHORIZATION, AUTH_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").value("Information retrieved"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("Product"));
    }

    @Test
    public void testTakeProduct() throws Exception {
        List<ProductDTO> productDTOs = Collections.singletonList(new ProductDTO());
        doNothing().when(productService).addProductToCell(anyInt(), anyList());

        mockMvc.perform(post("http://localhost:8085/api/v1/worker/create")
                        .param("userId", "1")
                        .header(HttpHeaders.AUTHORIZATION, AUTH_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTOs)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").value("Product added"))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void testShipProduct() throws Exception {
        List<ShipDTO> shipDTOs = Collections.singletonList(new ShipDTO(1));
        doNothing().when(productService).shipProduct(anyInt(), anyList());

        mockMvc.perform(delete("/api/v1/worker/ship")
                        .param("userId", "1")
                        .header(HttpHeaders.AUTHORIZATION, AUTH_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shipDTOs)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").value("Product shipped"))
                .andExpect(jsonPath("$.data").isArray());
    }
}
