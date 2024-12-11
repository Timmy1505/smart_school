package zm.schools.smartschool.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private Long billingRecordId;
    private double amount;
    private String referenceNumber;
}
