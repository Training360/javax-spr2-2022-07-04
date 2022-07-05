package empapp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Employee employee;

    public Address(String city) {
        this.city = city;
    }
}
