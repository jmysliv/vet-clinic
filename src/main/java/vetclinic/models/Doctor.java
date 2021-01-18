package vetclinic.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = Doctor.TABLE_NAME)
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = Columns.ID)
    private int id;

    @Column(name = Columns.NAME, nullable = false)
    private String name;

    public static final String TABLE_NAME = "doctors";

    public static class Columns {

        public static final String ID = "id";

        public static final String NAME = "name";

    }
}
