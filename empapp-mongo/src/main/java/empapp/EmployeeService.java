package empapp;

import org.modelmapper.ModelMapper;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Mono<EmployeeDto> createEmployee(Mono<CreateEmployeeCommand> command) {
        return command
                .map(this::toEmployee)
                .flatMap(employeeRepository::save)
                .map(this::toEmployeeDto);
    }

    public Flux<EmployeeDto> listEmployees() {
        return employeeRepository
                .findAll()
                .map(this::toEmployeeDto);
    }

    public Mono<EmployeeDto> findEmployeeById(String id) {
        return employeeRepository.findById(id)
                .map(this::toEmployeeDto);
    }

    public Mono<EmployeeDto> updateEmployee(String id, Mono<UpdateEmployeeCommand> command) {
                return command
                .zipWith(employeeRepository.findById(id))
                .doOnNext(t -> t.getT2().setName(t.getT1().getName()))
                .map(t -> t.getT2())
                .flatMap(e -> employeeRepository.save(e))
                .map(this::toEmployeeDto);
    }

    public Mono<String> deleteEmployee(String id) {
        return employeeRepository
                .findById(id)
                .doOnNext(System.out::println)
                .zipWith(employeeRepository.deleteById(id).thenReturn(id))
                .map(t -> t.getT2());
    }

    private EmployeeDto toEmployeeDto(Employee employee) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(employee, EmployeeDto.class);
    }

    private Employee toEmployee(CreateEmployeeCommand command) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(command, Employee.class);
    }
}
