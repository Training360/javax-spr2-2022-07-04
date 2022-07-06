package empapp;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    private EmployeesMessageGateway gateway;

    public EmployeeDto createEmployee(CreateEmployeeCommand command) {
        Employee employee = new Employee(command.getName());
        ModelMapper modelMapper = new ModelMapper();
        if (command.getAddresses() != null) {
            employee.addAddresses(command.getAddresses().stream().map(a -> modelMapper.map(a, Address.class)).collect(Collectors.toList()));
        }
        employeeRepository.save(employee);

        gateway.sendMessage(String.format("Employee has been createed: %s, id: %d", command.getName(), employee.getId()));

        return modelMapper.map(employee, EmployeeDto.class);
    }

    public List<EmployeeDto> listEmployees() {
        ModelMapper modelMapper = new ModelMapper();
        return employeeRepository.findAllWithAddresses().stream()
                .map(e -> modelMapper.map(e, EmployeeDto.class))
                .collect(Collectors.toList());
    }

    public EmployeeDto findEmployeeById(long id) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(employeeRepository.findByIdWithAddresses(id)
                        .orElseThrow(() -> new NotFoundException("Employee not found with id: " + id)),
                EmployeeDto.class);
    }

    @Transactional
    public EmployeeDto updateEmployee(long id, UpdateEmployeeCommand command) {
        Employee employeeToModify = employeeRepository.getById(id);
        employeeToModify.setName(command.getName());
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(employeeToModify, EmployeeDto.class);
    }

    public void deleteEmployee(long id) {
        Employee employee = employeeRepository.findByIdWithAddresses(id)
                .orElseThrow(() -> new NotFoundException("Employee not found with id: " + id));
        employeeRepository.delete(employee);
    }
}
