package empapp;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class CxfConfig {

    @Autowired
    private Bus bus;

    @Bean
    public Endpoint employeeEndpoint(EmployeeWebService employeeWebService) {
        EndpointImpl endpoint = new EndpointImpl(bus, employeeWebService);
        endpoint.publish("/employees");
        return endpoint;
    }
}
