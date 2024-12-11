package zm.schools.smartschool.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import zm.schools.smartschool.DTOs.SchoolDto;
import zm.schools.smartschool.Services.SchoolService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/admin/school")
@AllArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    @GetMapping("index")
    public String getDashboard() {
        return ("dashboard/index");
    }
    

    @PostMapping
    public ResponseEntity<SchoolDto> createSchool(@RequestBody SchoolDto schoolDto) {
        SchoolDto createdSchool = schoolService.createSchool(schoolDto);
        SchoolDto responseDto = convertToResponseDto(createdSchool);
        return ResponseEntity.created(URI.create("/admin/school/" + createdSchool.getId())).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolDto> getSchoolById(@PathVariable Long id) {
        SchoolDto school = schoolService.getSchoolById(id);
        SchoolDto responseDto = convertToResponseDto(school);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<SchoolDto>> getAllSchools() {
        List<SchoolDto> schools = schoolService.getAllSchools();
        List<SchoolDto> responseDtos = schools.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolDto> updateSchool(@PathVariable Long id, @RequestBody SchoolDto schoolDto) {
        SchoolDto updatedSchool = schoolService.updateSchool(id, schoolDto);
        SchoolDto responseDto = convertToResponseDto(updatedSchool);
        return ResponseEntity.ok(responseDto);
    }


    private SchoolDto convertToResponseDto(SchoolDto school) {
        return new SchoolDto(
                school.getId(),
                school.getSchoolName(),
                school.getCentreNumber(),
                school.getPoBox(),
                school.getIsPrivate(),
                school.getCategory(),
                school.getAcademicYear(),
                school.getCurrentTerm(),
                school.getStartOfSession(),
                school.getEndOfSession()
        );
    }
}
