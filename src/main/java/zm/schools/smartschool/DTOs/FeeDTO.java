package zm.schools.smartschool.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeeDTO {
    private Long id;
    private String name;
    private String description;
    private double amount;
    private boolean compulsory;
    private String paymentFrequency; // "termly" or "yearly"
    private Long schoolId;
}

