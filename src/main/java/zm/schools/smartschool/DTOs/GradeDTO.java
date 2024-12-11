package zm.schools.smartschool.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeDTO {
   
  
    private Long id;
    private Long schoolId;
    private MasterGradeDTO masterGrade;

   

    
}
