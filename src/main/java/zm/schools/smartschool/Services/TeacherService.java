package zm.schools.smartschool.Services;
import java.util.List;

public interface TeacherService  {
    
    void assignGradesToTeacher(Long teacherId, List<Long> gradeIds);
    void assignSubjectsToTeacher(Long teacherId, List<Long> subjectIds);
}
