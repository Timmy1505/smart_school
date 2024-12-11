package zm.schools.smartschool.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zm.schools.smartschool.Enums.Term;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolDto {
    private Long id;
    private String schoolName;
    private String centreNumber;
    private String poBox;
    private Boolean isPrivate;
    private String category;
    private Integer academicYear;
    private Term currentTerm;
    private LocalDate startOfSession;
    private LocalDate endOfSession;
}
