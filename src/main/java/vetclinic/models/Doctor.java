package vetclinic.models;

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
@Table(name = Doctor.TABLE_NAME)
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = Columns.ID, unique = true, nullable = false)
    private int id;

    @NotNull
    @Size(max = 24)
    @Column(name = Columns.NAME, nullable = false, length = 24)
    private String name;

    public static final String TABLE_NAME = "doctors";

    public static class Columns {

        public static final String ID = "id";

        public static final String NAME = "name";

    }
}
