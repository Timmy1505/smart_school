package zm.schools.smartschool.DTOs;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectResponse {
    private Long id;
    private String name;
    private boolean compulsory;
    private List<GradeDTO> grades;
}
