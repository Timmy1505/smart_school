package zm.schools.smartschool.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import zm.schools.smartschool.DTOs.GradeDTO;
import zm.schools.smartschool.DTOs.SectionDto;
import zm.schools.smartschool.Services.SchoolDetailsService;

import java.util.List;

@RestController
@RequestMapping("/api/schools")
@AllArgsConstructor
public class SchoolDetailsController {

    private final SchoolDetailsService schoolDetailsService;



    @GetMapping("/{schoolId}/grades")
    public ResponseEntity<List<GradeDTO>> getGradesBySchoolId(@PathVariable Long schoolId) {
        List<GradeDTO> grades = schoolDetailsService.getGradesBySchoolId(schoolId);
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/{schoolId}/sections")
    public ResponseEntity<List<SectionDto>> getSectionsBySchoolId(@PathVariable Long schoolId) {
        List<SectionDto> sections = schoolDetailsService.getSectionsBySchoolId(schoolId);
        return ResponseEntity.ok(sections);
    }
}
