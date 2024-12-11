package zm.schools.smartschool.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDTO {

    private Long id;
    private AppUserDTO appUserDTO;
    private String gradeName;
    private String sectionName;
    private String examinationNumber;
    private GuardianDto guardianDto;  
}