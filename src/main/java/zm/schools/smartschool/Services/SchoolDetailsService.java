package zm.schools.smartschool.Services;

import java.util.List;

import zm.schools.smartschool.DTOs.GradeDTO;
import zm.schools.smartschool.DTOs.SectionDto;

public interface SchoolDetailsService {
    List<GradeDTO> getGradesBySchoolId(Long schoolId);
    List<SectionDto> getSectionsBySchoolId(Long schoolId);
}
