package zm.schools.smartschool.Models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zm.schools.smartschool.Enums.Term;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcademicSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int year;
    private Term currentTerm;
    private LocalDate startOfSession;
    private LocalDate endOfSession;
}
