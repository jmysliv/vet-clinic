package vetclinic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vetclinic.models.AppointmentSlot;

@Repository
public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, Integer> {
}
