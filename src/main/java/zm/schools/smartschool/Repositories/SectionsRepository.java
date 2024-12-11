package zm.schools.smartschool.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import zm.schools.smartschool.Models.Section;





public interface SectionsRepository extends JpaRepository<Section, Long> {

    List<Section> findBySchoolId(Long schoolId);
}