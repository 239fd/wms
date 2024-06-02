package by.wms.server.DTO;

import by.wms.server.Entity.Enum.Title;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignUpDTOTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        SignUpDTO signUpDTO = new SignUpDTO();
        signUpDTO.setId(1);
        signUpDTO.setLogin("testLogin");
        signUpDTO.setPassword("testPassword");
        signUpDTO.setPhone("123456789");
        signUpDTO.setTitle(Title.accountant);
        signUpDTO.setFirstName("John");
        signUpDTO.setSecondName("Doe");
        signUpDTO.setSurname("Smith");
        signUpDTO.setOrganizationId("testOrgId");

        // Act and Assert
        assertEquals(1, signUpDTO.getId());
        assertEquals("testLogin", signUpDTO.getLogin());
        assertEquals("testPassword", signUpDTO.getPassword());
        assertEquals("123456789", signUpDTO.getPhone());
        assertEquals(Title.accountant, signUpDTO.getTitle());
        assertEquals("John", signUpDTO.getFirstName());
        assertEquals("Doe", signUpDTO.getSecondName());
        assertEquals("Smith", signUpDTO.getSurname());
        assertEquals("testOrgId", signUpDTO.getOrganizationId());
    }
}