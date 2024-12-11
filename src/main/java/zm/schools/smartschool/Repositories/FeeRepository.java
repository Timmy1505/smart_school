package zm.schools.smartschool.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import zm.schools.smartschool.Models.Fee;



public interface FeeRepository extends JpaRepository<Fee, Long> {
    List<Fee> findAllByCompulsory(boolean compulsory);
}
