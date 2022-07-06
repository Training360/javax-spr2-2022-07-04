package empapp;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

@WebService
@Service
@AllArgsConstructor
public class EmployeeWebService {

    private EmployeeService employeeService;

    public EmployeeDto findById(long id) {
        return employeeService.findEmployeeById(id);
    }
}
