package zm.schools.smartschool.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterGradeDTO {
    private Long id;
    private String name;  // Assuming MasterGrade has a name field
}
