package vetclinic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vetclinic.models.Appointment;

import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query(value = "select * from appointments a where a.slot_id = :slotId", nativeQuery = true)
    Optional<Appointment> findBySlotId(@Param("slotId") int slotId);
}
