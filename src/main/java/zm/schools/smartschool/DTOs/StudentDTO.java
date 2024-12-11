package zm.schools.smartschool.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

   
    private Long id;
    private AppUserDTO appUser;
    private Long gradeId;
    private Long sectionId;
    private String examinationNumber;  
    private GuardianDto guardianDto;  
}
