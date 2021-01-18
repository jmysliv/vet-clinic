package vetclinic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vetclinic.models.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
}
