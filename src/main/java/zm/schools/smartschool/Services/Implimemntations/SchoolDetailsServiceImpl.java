package zm.schools.smartschool.Services.Implimemntations;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import zm.schools.smartschool.DTOs.GradeDTO;
import zm.schools.smartschool.DTOs.MasterGradeDTO;
import zm.schools.smartschool.DTOs.SectionDto;
import zm.schools.smartschool.Exceptions.ResourceNotFoundException;
import zm.schools.smartschool.Models.Grade;
import zm.schools.smartschool.Models.Section;
import zm.schools.smartschool.Repositories.GradeRepository;
import zm.schools.smartschool.Repositories.SchoolRepository;
import zm.schools.smartschool.Repositories.SectionsRepository;
import zm.schools.smartschool.Services.SchoolDetailsService;

import java.util.List;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class SchoolDetailsServiceImpl implements SchoolDetailsService {

    private final GradeRepository gradeRepository;
    private final SectionsRepository sectionRepository;
    private final SchoolRepository schoolRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<GradeDTO> getGradesBySchoolId(Long schoolId) {
        if (!schoolRepository.existsById(schoolId)) {
            throw new ResourceNotFoundException("School", "id", schoolId);
        }
        List<Grade> grades = gradeRepository.findBySchoolId(schoolId);
        return grades.stream()
                .map(grade -> {
                    MasterGradeDTO masterGradeDTO = modelMapper.map(grade.getMasterGrade(), MasterGradeDTO.class);
                    
                    return new GradeDTO(
                            grade.getId(),
                            grade.getSchool().getId(),
                            masterGradeDTO
                    );
                })
                .collect(Collectors.toList());
    }
     @Override
    public List<SectionDto> getSectionsBySchoolId(Long schoolId) {
        if (!schoolRepository.existsById(schoolId)) {
            throw new ResourceNotFoundException("School ", "id", schoolId);
        }
        List<Section> sections = sectionRepository.findBySchoolId(schoolId);
        return sections.stream()
                .map(section -> modelMapper.map(section, SectionDto.class))
                .collect(Collectors.toList());
    }
}
