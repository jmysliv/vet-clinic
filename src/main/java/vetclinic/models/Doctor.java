package vetclinic.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = Doctor.TABLE_NAME)
public class Doctor {
    @Id
    @Column(name = Columns.ID, nullable = false, unique = true, length = 4)
    @Size(min= 4, max= 4)
    private String id;

    @Column(name = Columns.NAME, nullable = false)
    private String name;

    @Column(name = Columns.PIN, nullable = false, length = 4)
    @Size(min= 4, max= 4)
    private String pin;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    private Set<AppointmentSlot> appointmentSlots = new HashSet<>();

    public static final String TABLE_NAME = "doctors";
    public static class Columns {

        public static final String ID = "id";

        public static final String NAME = "name";

        public static final String PIN = "pin";

    }
}
