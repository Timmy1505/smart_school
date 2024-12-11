package zm.schools.smartschool.Services;

import java.util.List;

import zm.schools.smartschool.DTOs.GradeDTO;

public interface GradeService {
    GradeDTO createGrade(GradeDTO gradeDTO);
    GradeDTO updateGrade(Long id, GradeDTO gradeDTO);
    GradeDTO getGradeById(Long id);
    List<GradeDTO> getAllGrades();
    void deleteGrade(Long id);
}

