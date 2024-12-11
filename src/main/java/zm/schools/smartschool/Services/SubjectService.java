package zm.schools.smartschool.Services;

import java.util.List;

import zm.schools.smartschool.DTOs.SubjectRequest;
import zm.schools.smartschool.DTOs.SubjectResponse;
public interface SubjectService {
    void saveSubject(SubjectRequest subjectDTO);
    SubjectResponse getSubject(Long id);
    List<SubjectResponse> getAllSubjects();
    void deleteSubject(Long id);
}
