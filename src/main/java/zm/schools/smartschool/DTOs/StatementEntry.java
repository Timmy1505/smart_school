package zm.schools.smartschool.DTOs;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatementEntry {
    private Date date;
    private String reference;
    private String description;
    private double debit;
    private double credit;
    private double runningBalance;
}


