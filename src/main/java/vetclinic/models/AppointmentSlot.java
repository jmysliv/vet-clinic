package vetclinic.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = AppointmentSlot.TABLE_NAME)
public class AppointmentSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = Columns.ID)
    private int id;

    @Column(name = Columns.DATE_TIME)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Columns.DOCTOR_ID)
    private Doctor doctor;

    @OneToOne(mappedBy = "appointmentSlot")
    private Appointment appointment;

    public static final String TABLE_NAME = "appointment_slots";
    public static class Columns {

        public static final String ID = "id";

        public static final String DOCTOR_ID = "doctor_id";

        public static final String DATE_TIME = "date_time";

    }
}
