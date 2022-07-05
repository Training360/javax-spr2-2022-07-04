package empapp;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
@Slf4j
public class EmployeeController {

    private EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDto> employees() {
        return employeeService.listEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> findEmployeeById(@PathVariable("id") long id) {
        EmployeeDto employee = employeeService.findEmployeeById(id);
        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(Duration.ofMinutes(2)))
                //.eTag(Integer.toString(employee.hashCode()))
                .eTag(Integer.toString(employee.getVersion()))
                .body(employee);
    }

    @PostMapping // nem idempotens
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "creates an employee")
    public EmployeeDto createEmployee(@RequestBody CreateEmployeeCommand command) {
        return employeeService.createEmployee(command);
    }

    @PutMapping("/{id}") // idempotens
    public EmployeeDto updateEmployee(@PathVariable("id") long id, @RequestBody UpdateEmployeeCommand command,
                                      @RequestHeader("If-none-match") Optional<String> version) {
        log.info("Fogadjunk: " + version.get());
        Optional<Integer> converted = version
                .map(s -> s.replace("\"", ""))
                .map(Integer::parseInt);

        return employeeService.updateEmployee(id, command, converted);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable("id") long id) {
        employeeService.deleteEmployee(id);
    }

}
