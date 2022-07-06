package empapp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public Flux<EmployeeDto> employees() {
        return employeeService.listEmployees();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<EmployeeDto>> findEmployeeById(@PathVariable("id") String id) {
        return employeeService.findEmployeeById(id).map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<EmployeeDto> createEmployee(@RequestBody Mono<CreateEmployeeCommand> command) {
        return employeeService.createEmployee(command);
    }

    @PostMapping("/{id}")
    public Mono<EmployeeDto> updateEmployee(@PathVariable("id") String id, @RequestBody Mono<UpdateEmployeeCommand> command) {
        return employeeService.updateEmployee(id, command);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> deleteEmployee(@PathVariable("id") String id) {
        return employeeService
                .deleteEmployee(id)
                .map(v -> ResponseEntity.status(HttpStatus.NO_CONTENT).build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
