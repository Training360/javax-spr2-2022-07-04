package empapp;

import lombok.Value;

@Value
public class EmployeeCreatedEvent {

    private EmployeeDto employeeDto;

}
