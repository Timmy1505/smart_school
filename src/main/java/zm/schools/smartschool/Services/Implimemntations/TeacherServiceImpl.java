package zm.schools.smartschool.Services.Implimemntations;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import zm.schools.smartschool.DTOs.TeacherDTO;
import zm.schools.smartschool.DTOs.TeacherRequest;
import zm.schools.smartschool.Exceptions.ResourceNotFoundException;
import zm.schools.smartschool.Models.AuthUser;
import zm.schools.smartschool.Models.Grade;
import zm.schools.smartschool.Models.Subject;
import zm.schools.smartschool.Repositories.AuthUserRepository;
import zm.schools.smartschool.Repositories.GradeRepository;
import zm.schools.smartschool.Repositories.SubjectRepository;
import zm.schools.smartschool.Services.TeacherService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService  {

   
    private final AuthUserRepository teacherRepository;

   
    private final GradeRepository gradeRepository;

   
    private final SubjectRepository subjectRepository;

   
    private final ModelMapper modelMapper;

    // @Override
    // public TeacherDTO createTeacher(TeacherRequest teacherRequest) {
    //     // Convert TeacherRequest to Teacher
    // AuthUser teacher = modelMapper.map(teacherRequest, AuthUser.class);

    // // Fetch grades and subjects from the database
    // List<Grade> grades = gradeRepository.findAllById(teacherRequest.getGradeIds());
    // List<Subject> subjects = subjectRepository.findAllById(teacherRequest.getSubjectIds());

    // // Set grades and subjects in the teacher entity
    // teacher.setGrades(grades);
    // teacher.setSubjects(subjects);

    // // Save the teacher entity
    // teacher = teacherRepository.save(teacher);

    // // Convert the saved teacher to TeacherDTO and return it
    // return modelMapper.map(teacher, TeacherDTO.class);}


    @Override
    public void assignGradesToTeacher(Long teacherId, List<Long> gradeIds) {
       AuthUser teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", teacherId));
        List<Grade> grades = gradeRepository.findAllById(gradeIds);
        teacher.setGrades(grades);
        teacherRepository.save(teacher);
    }

    @Override
    public void assignSubjectsToTeacher(Long teacherId, List<Long> subjectIds) {
       AuthUser teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", teacherId));
        List<Subject> subjects = subjectRepository.findAllById(subjectIds);
        teacher.setSubjects(subjects);
        teacherRepository.save(teacher);
    }
}

