package empapp;


import java.net.URL;

public class EmployeesMain {

    public static void main(String[] args) throws Exception {
        var service = new EmployeeWebServiceService(new URL("http://localhost:8080/services/employees?wsdl"));
        var port = service.getEmployeeWebServicePort();
        var employee = port.findById(1);
        System.out.println(employee.getName());

    }
}
