package by.wms.server.Controllers;

import by.wms.server.DTO.RevaluationDTO;
import by.wms.server.DTO.ShipDTO;
import by.wms.server.DTO.TableDTO;
import by.wms.server.DTO.WriteOffDTO;
import by.wms.server.Service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountantController.class)
public class AccountantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInventory() throws Exception {
        List<ShipDTO> shipDTOList = Arrays.asList(new ShipDTO(12));
        doNothing().when(productService).inventoryOfProduct(anyInt(), anyList());

        mockMvc.perform(put("/api/v1/accountant/inventory")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shipDTOList)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":true,\"message\":\"Inventory completed\",\"data\":[{\"number\":1,\"tax\":100}]}"));
    }

    @Test
    public void testTakeInfo() throws Exception {
        List<TableDTO> tableDTOList = Arrays.asList(new TableDTO(1, "Product 1", Date.valueOf("2022-01-01")));
        when(productService.takeInfo(anyInt())).thenReturn(tableDTOList);

        mockMvc.perform(get("/api/v1/accountant/revaluation")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":true,\"message\":\"Information taken\",\"data\":[{\"number\":1,\"name\":\"Product 1\",\"date\":\"2024-01-01\"}]}"));
    }

    @Test
    public void testWriteOff() throws Exception {
        List<WriteOffDTO> writeOffDTOList = Arrays.asList(new WriteOffDTO(1, "брак"));
        doNothing().when(productService).writeOff(anyInt(), anyList());

        mockMvc.perform(put("/api/v1/accountant/writeoff")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(writeOffDTOList)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":true,\"message\":\"Write Off completed\",\"data\":[{\"number\":1,\"amount\":100}]}"));
    }

    @Test
    public void testRevaluate() throws Exception {
        List<RevaluationDTO> revaluationDTOList = Arrays.asList(new RevaluationDTO(2312, 5, 8, 12312), new RevaluationDTO(2312, 5, 9, 123122));
        doNothing().when(productService).revalueProduct(anyInt(), anyList());

        mockMvc.perform(put("/api/v1/accountant/revaluation")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(revaluationDTOList)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":true,\"message\":\"Product revalued successfully\",\"data\":[{\"numberOfAgreement\":2312,\"amount\":5,\"number\":8,\"cost\":12312},{\"numberOfAgreement\":2312,\"amount\":5,\"number\":9,\"cost\":123122}]}"));
    }
}
