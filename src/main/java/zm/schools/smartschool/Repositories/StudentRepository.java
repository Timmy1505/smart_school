package zm.schools.smartschool.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import zm.schools.smartschool.Models.Grade;
import zm.schools.smartschool.Models.Student;


public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByExaminationNumber(String examinationNumber);
    List<Student> findAllByGrade(Grade grade);
}
