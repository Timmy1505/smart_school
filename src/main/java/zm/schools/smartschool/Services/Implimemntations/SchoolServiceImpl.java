package zm.schools.smartschool.Services.Implimemntations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import zm.schools.smartschool.Configs.StringUtil;
import zm.schools.smartschool.DTOs.SchoolDto;
import zm.schools.smartschool.Exceptions.ResourceNotFoundException;
import zm.schools.smartschool.Models.AcademicSession;
import zm.schools.smartschool.Models.Grade;
import zm.schools.smartschool.Models.MasterGrade;
import zm.schools.smartschool.Models.School;
import zm.schools.smartschool.Repositories.GradeRepository;
import zm.schools.smartschool.Repositories.MasterGradeRepository;
import zm.schools.smartschool.Repositories.SchoolRepository;
import zm.schools.smartschool.Services.SchoolService;

@Service
@AllArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;
    private final GradeRepository gradeRepository;
    private final MasterGradeRepository masterGradeRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public SchoolDto createSchool(SchoolDto schoolDTO) {
        School school = new School();
        school.setSchoolName(StringUtil.capitalizeWords(schoolDTO.getSchoolName()));
        school.setCentreNumber(StringUtil.capitalizeWords(schoolDTO.getCentreNumber()));
        school.setPoBox(StringUtil.capitalizeWords(schoolDTO.getPoBox()));
        school.setIsPrivate(schoolDTO.getIsPrivate());
        school.setCategory(schoolDTO.getCategory());

        AcademicSession academicSession = new AcademicSession();
        Integer academicYear = schoolDTO.getAcademicYear();
        if (academicYear != null) {
            academicSession.setYear(academicYear);
        } else {
            throw new IllegalArgumentException("Academic year cannot be null");
        }
        academicSession.setCurrentTerm(schoolDTO.getCurrentTerm());
        academicSession.setStartOfSession(schoolDTO.getStartOfSession());
        academicSession.setEndOfSession(schoolDTO.getEndOfSession());

        school.setAcademicSession(academicSession);

        School savedSchool = schoolRepository.save(school);

        seedGrades(savedSchool);

        return modelMapper.map(savedSchool, SchoolDto.class);
    }

    private void seedGrades(School school) {
        List<String> primaryGrades = Arrays.asList("baby", "reception", "1", "2", "3", "4", "5", "6", "7");
        List<String> secondaryGrades = Arrays.asList("8", "9", "10", "11", "12");

        if ("PRIMARY".equalsIgnoreCase(school.getCategory())) {
            addGradesToSchool(school, primaryGrades);
        } else if ("SECONDARY".equalsIgnoreCase(school.getCategory())) {
            addGradesToSchool(school, secondaryGrades);
        }
    }

    private void addGradesToSchool(School school, List<String> gradeNames) {
        for (String gradeName : gradeNames) {
            Optional<MasterGrade> optionalMasterGrade = masterGradeRepository.findByName(gradeName);
            if (optionalMasterGrade.isPresent()) {
                MasterGrade masterGrade = optionalMasterGrade.get();
                if (!gradeRepository.existsByMasterGradeAndSchool(masterGrade, school)) {
                    Grade grade = new Grade();
                    grade.setMasterGrade(masterGrade);
                    grade.setSchool(school);
                    gradeRepository.save(grade);
                }
            }
        }
    }

    @Override
    public SchoolDto getSchoolById(Long id) {
        Optional<School> foundschool = schoolRepository.findById(id);
        School school = foundschool.orElseThrow(() -> new ResourceNotFoundException("School" , "id", id));
        return modelMapper.map(school, SchoolDto.class);
    }

    @Override
    public List<SchoolDto> getAllSchools() {
        List<School> schools = schoolRepository.findAll();
        return schools.stream()
                .map(school -> modelMapper.map(school, SchoolDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public SchoolDto updateSchool(Long id, SchoolDto schoolDto) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School" , "id", id));

        school.setSchoolName(StringUtil.capitalizeWords(schoolDto.getSchoolName()));
        school.setCentreNumber(StringUtil.capitalizeWords(schoolDto.getCentreNumber()));
        school.setPoBox(StringUtil.capitalizeWords(schoolDto.getPoBox()));
        school.setIsPrivate(schoolDto.getIsPrivate());
        school.setCategory(schoolDto.getCategory());

        AcademicSession academicSession = school.getAcademicSession();
        academicSession.setYear(schoolDto.getAcademicYear());
        academicSession.setCurrentTerm(schoolDto.getCurrentTerm());
        academicSession.setStartOfSession(schoolDto.getStartOfSession());
        academicSession.setEndOfSession(schoolDto.getEndOfSession());

        school.setAcademicSession(academicSession);

        School updatedSchool = schoolRepository.save(school);
        return modelMapper.map(updatedSchool, SchoolDto.class);
    }

}
