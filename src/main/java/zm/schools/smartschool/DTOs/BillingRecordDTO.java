package zm.schools.smartschool.DTOs;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingRecordDTO {
    private Long id;
    private Long studentId;
    private Long feeId;
    private Date billingDate;
    private double amount;
    private boolean paid;
    private Date paymentDate;
}

