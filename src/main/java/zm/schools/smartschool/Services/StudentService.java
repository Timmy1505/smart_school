package zm.schools.smartschool.Services;

import zm.schools.smartschool.DTOs.StudentDTO;
import zm.schools.smartschool.DTOs.StudentResponseDTO;

public interface StudentService {
    StudentResponseDTO createStudent(StudentDTO studentDTO);
    StudentResponseDTO  updateStudentByExaminationNumber(String examinationNumber, StudentDTO studentDTO);
    StudentResponseDTO  getStudentByExaminationNumber(String examinationNumber);
}
