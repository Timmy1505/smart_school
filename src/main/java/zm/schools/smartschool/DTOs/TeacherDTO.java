package zm.schools.smartschool.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zm.schools.smartschool.Enums.Gender;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private AddressDto address;
    private Long schoolId;
    private String employeeNumber;
    private String nrc;
    private List<GradeDTO> grades;
    private List<SubjectResponse> subjects;
}
