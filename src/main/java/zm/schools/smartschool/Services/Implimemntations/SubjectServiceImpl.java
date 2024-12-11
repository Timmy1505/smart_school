package zm.schools.smartschool.Services.Implimemntations;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import zm.schools.smartschool.DTOs.GradeDTO;
import zm.schools.smartschool.DTOs.SubjectRequest;
import zm.schools.smartschool.DTOs.SubjectResponse;
import zm.schools.smartschool.Models.Grade;
import zm.schools.smartschool.Models.Subject;
import zm.schools.smartschool.Repositories.GradeRepository;
import zm.schools.smartschool.Repositories.SubjectRepository;
import zm.schools.smartschool.Services.SubjectService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubjectServiceImpl implements SubjectService {


    private SubjectRepository subjectRepository;

    private final GradeRepository gradeRepository;


    private final ModelMapper modelMapper;
    @Override
    public void saveSubject(SubjectRequest subjectDTO) {
        Subject subject = modelMapper.map(subjectDTO, Subject.class);
        List<Grade> grades = subjectDTO.getGradeIds().stream()
                .map(gradeId -> gradeRepository.findById(gradeId).orElseThrow(() -> new RuntimeException("Grade not found")))
                .collect(Collectors.toList());
        subject.setGrades(grades);
        subjectRepository.save(subject);
    }

    @Override
    public SubjectResponse getSubject(Long id) {
        Subject subject = subjectRepository.findById(id).orElse(null);
        return modelMapper.map(subject, SubjectResponse.class);
    }

    @Override
       public List<SubjectResponse> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        return subjects.stream()
                .map(subject -> {
                    SubjectResponse subjectDTO = modelMapper.map(subject, SubjectResponse.class);
                    List<GradeDTO> gradeDTOs = subject.getGrades().stream()
                        .map(grade -> modelMapper.map(grade, GradeDTO.class))
                        .collect(Collectors.toList());
                    subjectDTO.setGrades(gradeDTOs);
                    return subjectDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }
}