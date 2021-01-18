package vetclinic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vetclinic.models.AppointmentSlot;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, Integer> {
    @Query(value = "select * from appointment_slots a" +
            " where a.doctor_id = :doctorId" +
            " and a.date_time >= :startTime" +
            " and a.date_time < :endTime", nativeQuery = true)
    List<AppointmentSlot> findByDoctorIdAndDateTime(@Param("doctorId") int doctorId,
                                                    @Param("startTime") LocalDateTime startTime,
                                                    @Param("endTime") LocalDateTime endTime);
}
