package vetclinic.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = Appointment.TABLE_NAME)
public class Appointment {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = Columns.ID, unique = true, nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Columns.CUSTOMER_ID, nullable = false)
    private Customer customer;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Columns.SLOT_ID, nullable = false)
    private AppointmentSlot appointmentSlot;

    public static final String TABLE_NAME = "appointments";

    public static class Columns {

        public static final String ID = "id";

        public static final String CUSTOMER_ID = "customer_id";

        public static final String SLOT_ID = "slot_id";

    }
}
