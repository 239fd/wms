package by.wms.server.Service;

import by.wms.server.DTO.CredentialsDTO;
import by.wms.server.DTO.EmployeesDTO;
import by.wms.server.DTO.SignUpDTO;
import by.wms.server.Entity.Employees;
import by.wms.server.Exceptions.AppException;
import by.wms.server.Mappers.EmployeesMapper;
import by.wms.server.Repository.EmployeesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EmployeesService {

    private final EmployeesRepository employeesRepository;
    private final EmployeesMapper employeesMapper;
    private final PasswordEncoder passwordEncoder;

    public EmployeesDTO findByLogin(String login) {
        Employees employee = employeesRepository.findByLogin(login)
                .orElseThrow(()-> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return employeesMapper.toEmployeesDTO(employee);
    }

    public EmployeesDTO login(CredentialsDTO credentials) {
        Employees employee = employeesRepository.findByLogin(credentials.getLogin())
                .orElseThrow(()-> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        if(passwordEncoder.matches(CharBuffer.wrap(credentials.getPassword()), employee.getPassword())){
            return employeesMapper.toEmployeesDTO(employee);
        }

        throw new AppException("Wrong password", HttpStatus.UNAUTHORIZED);
    }

    public EmployeesDTO register(SignUpDTO signUpDTO) {
        Optional<Employees> optionalEmployees = employeesRepository.findByLogin(signUpDTO.getLogin());

        if (optionalEmployees.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.CONFLICT);
        }

        Employees employees = employeesMapper.signUpToEmployee(signUpDTO);
        System.out.println("Employees entity before save: " + employees); // Логирование
        employees.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDTO.getPassword())));

        Employees savedEmployees = employeesRepository.save(employees);

        return employeesMapper.toEmployeesDTO(employees);
    }
}
