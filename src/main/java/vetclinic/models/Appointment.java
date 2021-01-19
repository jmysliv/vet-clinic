package vetclinic.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = Appointment.TABLE_NAME)
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = Columns.ID, unique = true, nullable = false)
    private int id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = Columns.CUSTOMER_ID, nullable = false)
    private Customer customer;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = Columns.SLOT_ID, nullable = false)
    private AppointmentSlot appointmentSlot;

    public static final String TABLE_NAME = "appointments";

    public static class Columns {

        public static final String ID = "id";

        public static final String CUSTOMER_ID = "customer_id";

        public static final String SLOT_ID = "slot_id";

    }
}
