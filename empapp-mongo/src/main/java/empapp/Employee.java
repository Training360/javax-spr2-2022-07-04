package empapp;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "employees")
@Data
@NoArgsConstructor
public class Employee {

    @Id
    private String id;

    private String name;

    private List<Address> addresses = new ArrayList<>();

    public Employee(String name) {
        this.name = name;
    }

}
