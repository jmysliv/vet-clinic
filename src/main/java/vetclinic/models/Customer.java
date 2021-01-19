package vetclinic.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = Customer.TABLE_NAME)
public class Customer {
    @Id
    @Column(name = Columns.ID, nullable = false, unique = true, length = 4)
    @Size(min = 4, max = 4)
    private String id;

    @NotNull
    @Size(max = 24)
    @Column(name = Columns.NAME, nullable = false, length = 24)
    private String name;

    @JsonIgnore
    @NotNull
    @Column(name = Columns.PIN, nullable = false, length = 4)
    @Size(min = 4, max = 4)
    private String pin;

    public static final String TABLE_NAME = "customers";

    public static class Columns {

        public static final String ID = "id";

        public static final String NAME = "name";

        public static final String PIN = "pin";

    }
}
